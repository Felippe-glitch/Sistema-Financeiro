"""
Seed script for FinanceiroDB (SQL Server) using Faker.
Generates large, diverse datasets with plausible relationships.

Tables covered: Banco, Conta, Empresa, Usuario, Movimentacao, Pagar, Receber

Connection: uses SQL Server via pyodbc, reading Spring Boot's application.properties by default
or explicit env variables. Adjust sizes via CLI args.
"""
from __future__ import annotations
import os
import random
import string
import hashlib
import argparse
from datetime import datetime, timedelta
from dataclasses import dataclass

import pyodbc
from faker import Faker

# ---------------------- Config ----------------------
DEFAULT_COUNTS = {
    "bancos": 10,
    "usuarios": 200,
    "empresas": 500,
    "contas": 200,
    "movimentacoes": 5000,
    "pagar": 3000,
    "receber": 3000,
}

FORMA_PAGAMENTO = [
    "DINHEIRO",
    "CARTAO_CREDITO",
    "CARTAO_DEBITO",
    "BOLETO",
    "PIX",
    "TRANSFERENCIA_BANCARIA",
]

TIPO_CONTA = ["CORRENTE", "POUPANCA"]

# Mapeamento conforme o modelo atual (int) na entidade Empresa
# 0 - Cliente, 1 - Fornecedor, 2 - Ambos
TIPO_EMPRESA = [0, 1, 2]
# 0 - Física, 1 - Jurídica
TIPO_PESSOA = [0, 1]

# ---------------------- Helpers ----------------------

def rnd_digits(n: int) -> str:
    return "".join(random.choices(string.digits, k=n))


def hash_password(pwd: str) -> str:
    return hashlib.sha256(pwd.encode("utf-8")).hexdigest()


@dataclass
class ConnCfg:
    server: str
    database: str
    username: str
    password: str
    port: int = 1433
    encrypt: bool = True
    trust_server_certificate: bool = True
    driver: str = "ODBC Driver 17 for SQL Server"


def get_conn(cfg: ConnCfg):
    # Build candidate connection strings; prefer TCP with explicit port and encryption
    encrypt_part = f"Encrypt={'yes' if cfg.encrypt else 'no'};"
    trust_part = f"TrustServerCertificate={'yes' if cfg.trust_server_certificate else 'no'};"

    candidates = []

    # If server already includes ",port" or "\\instance", use as-is
    if ("," in cfg.server) or ("\\" in cfg.server):
        candidates.append(
            f"DRIVER={{{cfg.driver}}};SERVER={cfg.server};DATABASE={cfg.database};UID={cfg.username};PWD={cfg.password};{encrypt_part}{trust_part}"
        )
        candidates.append(
            f"DRIVER={{{cfg.driver}}};SERVER=tcp:{cfg.server};DATABASE={cfg.database};UID={cfg.username};PWD={cfg.password};{encrypt_part}{trust_part}"
        )
    else:
        # server,port
        candidates.append(
            f"DRIVER={{{cfg.driver}}};SERVER={cfg.server},{cfg.port};DATABASE={cfg.database};UID={cfg.username};PWD={cfg.password};{encrypt_part}{trust_part}"
        )
        candidates.append(
            f"DRIVER={{{cfg.driver}}};SERVER=tcp:{cfg.server},{cfg.port};DATABASE={cfg.database};UID={cfg.username};PWD={cfg.password};{encrypt_part}{trust_part}"
        )

    last_err = None
    for cs in candidates:
        try:
            return pyodbc.connect(cs, autocommit=False)
        except Exception as e:
            last_err = e
            continue
    if last_err:
        raise last_err
    raise RuntimeError("Failed to build a usable connection string.")


def get_columns(cur, table_name: str) -> set[str]:
    cur.execute(
        "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?",
        (table_name,),
    )
    return {row[0] for row in cur.fetchall()}


def get_columns_info(cur, table_name: str) -> dict[str, str]:
    cur.execute(
        "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?",
        (table_name,),
    )
    return {row[0]: str(row[1]).lower() for row in cur.fetchall()}


def pick_col(columns: set[str], candidates: list[str]) -> str:
    for c in candidates:
        if c in columns:
            return c
    raise RuntimeError(f"None of the candidate columns found: {candidates} in {columns}")


def fetch_existing_values(cur, table: str, column: str) -> set:
    try:
        cur.execute(f"SELECT {column} FROM {table}")
        return {row[0] for row in cur.fetchall() if row and row[0] is not None}
    except Exception:
        return set()


def gen_unique_email(faker: Faker, existing: set[str], max_attempts: int = 10) -> str:
    for _ in range(max_attempts):
        email = faker.email()
        if email not in existing:
            existing.add(email)
            return email
        # add a numeric suffix to increase entropy
        local, _, domain = email.partition("@")
        candidate = f"{local}+{rnd_digits(4)}@{domain}"
        if candidate not in existing:
            existing.add(candidate)
            return candidate
    # fallback deterministic
    fallback = f"user{rnd_digits(8)}@example.org"
    existing.add(fallback)
    return fallback


# ---------------------- Insert functions ----------------------

def insert_bancos(cur, faker: Faker, n: int):
    ids = []
    # Use exact snake_case names and OUTPUT to reliably fetch identity
    insert_sql = "INSERT INTO banco (nome_banco) OUTPUT inserted.id_banco VALUES (?)"
    for _ in range(n):
        nome = faker.company() + " Banco"
        cur.execute(insert_sql, (nome,))
        row = cur.fetchone()
        ids.append(int(row[0]))
    return ids


def insert_usuarios(cur, faker: Faker, n: int):
    ids = []
    insert_sql = "INSERT INTO usuario (nome_usuario, email_usuario, senha_usuario) VALUES (?, ?, ?)"
    existing_emails = fetch_existing_values(cur, "usuario", "email_usuario")
    for _ in range(n):
        nome = faker.name()
        email = gen_unique_email(faker, existing_emails)
        senha = hash_password(faker.password(length=12))
        cur.execute(insert_sql, (nome, email, senha))
        cur.execute("SELECT CAST(SCOPE_IDENTITY() AS bigint)")
        ids.append(cur.fetchone()[0])
    return ids


def insert_empresas(cur, faker: Faker, n: int):
    ids = []
    insert_sql = (
        "INSERT INTO empresa (razao_social, nome_fantasia, tipo_empresa, cpf_cpnj, tipo_pessoa, email, telefone, "
        "rua, numero, bairro, cep, cidade, estado, pais) "
        "OUTPUT inserted.id_empresa VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    )

    col_types = get_columns_info(cur, "empresa")
    def is_text(t: str) -> bool:
        t = (t or "").lower()
        return any(x in t for x in ["char", "text", "nchar", "varchar", "nvarchar"])

    tipo_emp_is_text = is_text(col_types.get("tipo_empresa", ""))
    tipo_pes_is_text = is_text(col_types.get("tipo_pessoa", ""))

    emails_seen = fetch_existing_values(cur, "empresa", "email")
    tels_seen = fetch_existing_values(cur, "empresa", "telefone")

    for _ in range(n):
        razao = faker.company()
        fantasia = razao.split(" ")[0] + " " + faker.bs().split(" ")[0].capitalize()
        tipo_empresa_idx = random.choice(TIPO_EMPRESA)
        tipo_pessoa_idx = random.choice(TIPO_PESSOA)
        tipo_empresa = ["CLIENTE", "FORNECEDOR", "AMBOS"][tipo_empresa_idx] if tipo_emp_is_text else tipo_empresa_idx
        tipo_pessoa = ["FISICA", "JURIDICA"][tipo_pessoa_idx] if tipo_pes_is_text else tipo_pessoa_idx

        if tipo_pessoa_idx == 0:
            cpf_cnpj = f"{rnd_digits(3)}.{rnd_digits(3)}.{rnd_digits(3)}-{rnd_digits(2)}"
        else:
            cpf_cnpj = f"{rnd_digits(2)}.{rnd_digits(3)}.{rnd_digits(3)}/{rnd_digits(4)}-{rnd_digits(2)}"

        email = gen_unique_email(faker, emails_seen)
        telefone = None
        for _ in range(10):
            candidate = faker.msisdn()[0:11]
            if candidate not in tels_seen:
                telefone = candidate
                break
        if telefone is None:
            telefone = f"{rnd_digits(11)}"
        tels_seen.add(telefone)

        rua = faker.street_name()
        numero = random.randint(1, 999)
        bairro = faker.city_suffix()
        cep = f"{rnd_digits(5)}-{rnd_digits(3)}"
        cidade = faker.city()
        estado = faker.state()
        pais = faker.current_country()

        cur.execute(
            insert_sql,
            (
                razao,
                fantasia,
                tipo_empresa,
                cpf_cnpj,
                tipo_pessoa,
                email,
                telefone,
                rua,
                numero,
                bairro,
                cep,
                cidade,
                estado,
                pais,
            ),
        )
        result = cur.fetchone()
        if result and result[0] is not None:
            ids.append(int(result[0]))
        else:
            print(f"[WARN] Empresa inserida sem retorno de ID: {razao}")

    return ids


def insert_contas(cur, faker: Faker, n: int, banco_ids):
    ids = []
    insert_sql = (
        "INSERT INTO conta (agencia, conta, saldo, tipo_conta, status_conta, dv_conta, fk_banco) OUTPUT inserted.id_conta VALUES (?, ?, ?, ?, ?, ?, ?)"
    )
    if not banco_ids:
        raise RuntimeError("insert_contas: lista de banco_ids está vazia.")
    for i in range(n):
        agencia = rnd_digits(4)
        conta = rnd_digits(6)
        saldo = round(random.uniform(0, 100000), 2)
        tipo_conta = random.choice(TIPO_CONTA)
        status_conta = random.choice([0, 1])
        dv_conta = random.randint(0, 9)
        fk_banco = int(random.choice(banco_ids))
        try:
            cur.execute(insert_sql, (agencia, conta, saldo, tipo_conta, status_conta, dv_conta, fk_banco))
            result = cur.fetchone()
            if result and result[0] is not None:
                ids.append(int(result[0]))
            else:
                print(f"[WARN] Conta insert at index {i} did not return an ID (possible constraint violation or missing identity column). Skipping.")
        except Exception as e:
            print(f"[ERROR] Conta insert at index {i} failed: {e}\nData: agencia={agencia}, conta={conta}, saldo={saldo}, tipo_conta={tipo_conta}, status_conta={status_conta}, dv_conta={dv_conta}, fk_banco={fk_banco}")
    return ids


# Helper to pick a date window with variety (past, near due, future)

def random_date_window():
    today = datetime.now()
    scenario = random.choices(
        population=["past", "near", "future"],
        weights=[0.4, 0.3, 0.3],
        k=1,
    )[0]
    if scenario == "past":
        start = today - timedelta(days=random.randint(30, 365))
    elif scenario == "near":
        start = today - timedelta(days=random.randint(0, 30))
    else:
        start = today + timedelta(days=random.randint(1, 180))
    return start


def insert_movimentacoes(cur, n: int, conta_ids):
    ids = []
    insert_sql = (
        "INSERT INTO movimentacao (data_registro_movimentacao, forma_pagamento, valor_mov, tipo_duplicata, fk_conta, usuario_cad) "
        "VALUES (?, ?, ?, ?, ?, ?)"
    )
    for _ in range(n):
        data_reg = datetime.now() - timedelta(days=random.randint(0, 365))
        forma = random.choice(FORMA_PAGAMENTO)
        valor = round(random.uniform(10, 10000), 2)
        tipo_duplicata = random.choice([0, 1])
        fk_conta = random.choice(conta_ids)
        usuario = random.choice(["sistema", "job_import", "api", "admin"])
        cur.execute(insert_sql, (data_reg, forma, valor, tipo_duplicata, fk_conta, usuario))
        cur.execute("SELECT CAST(SCOPE_IDENTITY() AS bigint)")
        ids.append(cur.fetchone()[0])
    return ids


def insert_pagar(cur, faker: Faker, n: int, empresa_ids, conta_ids):
    ids = []
    insert_sql = (
        "INSERT INTO pagar (valor_pagar, data_vencimento, data_emissao, data_pagamento, descricao_pagar, usuario, fk_empresa, fk_conta) "
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    )
    if not empresa_ids:
        raise RuntimeError("insert_pagar: lista de empresa_ids está vazia. Nenhuma empresa foi criada ou houve erro na etapa anterior.")
    for i in range(n):
        base = random_date_window()
        data_emissao = base - timedelta(days=random.randint(1, 15))
        data_venc = base + timedelta(days=random.randint(0, 60))
        pago = random.random() < 0.55
        data_pag = data_venc + timedelta(days=random.randint(-10, 30)) if pago else None
        fk_empresa = random.choice(empresa_ids) if empresa_ids else None
        fk_conta = random.choice(conta_ids) if conta_ids else None
        if fk_empresa is None:
            print(f"[ERRO] fk_empresa está NULL na linha {i}. Verifique se empresas foram criadas corretamente.")
            raise RuntimeError("fk_empresa está NULL. Não é permitido inserir NULL em fk_empresa.")
        print(f"[INFO] Inserindo pagar: fk_empresa={fk_empresa}, fk_conta={fk_conta}")
        cur.execute(
            insert_sql,
            (
                round(random.uniform(50, 20000), 2),
                data_venc,
                data_emissao,
                data_pag,
                faker.sentence(nb_words=6),
                faker.user_name(),
                fk_empresa,
                fk_conta,
            ),
        )
        cur.execute("SELECT CAST(SCOPE_IDENTITY() AS bigint)")
        ids.append(cur.fetchone()[0])
    return ids


def insert_receber(cur, faker: Faker, n: int, empresa_ids, conta_ids):
    ids = []
    insert_sql = (
        "INSERT INTO receber (valor_receber, data_vencimento, data_emissao, data_recebimento, descricao_receber, usuario, fk_empresa, fk_conta) "
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
    )
    for _ in range(n):
        base = random_date_window()
        data_emissao = base - timedelta(days=random.randint(1, 15))
        data_venc = base + timedelta(days=random.randint(0, 60))
        recebido = random.random() < 0.6
        data_rec = data_venc + timedelta(days=random.randint(-15, 20)) if recebido else None
        cur.execute(
            insert_sql,
            (
                round(random.uniform(50, 25000), 2),
                data_venc,
                data_emissao,
                data_rec,
                faker.sentence(nb_words=6),
                faker.user_name(),
                random.choice(empresa_ids),
                random.choice(conta_ids),
            ),
        )
        cur.execute("SELECT CAST(SCOPE_IDENTITY() AS bigint)")
        ids.append(cur.fetchone()[0])
    return ids


# ---------------------- Main seeding flow ----------------------

def main():
    parser = argparse.ArgumentParser(description="Seed FinanceiroDB with realistic data")
    parser.add_argument("--server", default=os.getenv("SQLSERVER_HOST", "DESKTOP-H4LAA9J"))
    parser.add_argument("--database", default=os.getenv("SQLSERVER_DB", "FinanceiroDB"))
    parser.add_argument("--username", default=os.getenv("SQLSERVER_USER", "sa"))
    parser.add_argument("--password", default=os.getenv("SQLSERVER_PASSWORD", "1234"))
    parser.add_argument("--driver", default=os.getenv("SQLSERVER_DRIVER", "ODBC Driver 17 for SQL Server"))
    parser.add_argument("--port", type=int, default=int(os.getenv("SQLSERVER_PORT", 1433)))
    parser.add_argument("--no-encrypt", action="store_true", help="Disable connection encryption")
    parser.add_argument("--no-trust", action="store_true", help="Disable TrustServerCertificate")
    # scaling
    parser.add_argument("--bancos", type=int, default=DEFAULT_COUNTS["bancos"])
    parser.add_argument("--usuarios", type=int, default=DEFAULT_COUNTS["usuarios"])
    parser.add_argument("--empresas", type=int, default=DEFAULT_COUNTS["empresas"])
    parser.add_argument("--contas", type=int, default=DEFAULT_COUNTS["contas"])
    parser.add_argument("--movimentacoes", type=int, default=DEFAULT_COUNTS["movimentacoes"])
    parser.add_argument("--pagar", type=int, default=DEFAULT_COUNTS["pagar"])
    parser.add_argument("--receber", type=int, default=DEFAULT_COUNTS["receber"])
    parser.add_argument("--locale", default="pt_BR")
    args = parser.parse_args()

    faker = Faker(args.locale)

    cfg = ConnCfg(
        server=args.server,
        database=args.database,
        username=args.username,
        password=args.password,
        port=args.port,
        encrypt=(not args.no_encrypt),
        trust_server_certificate=(not args.no_trust),
        driver=args.driver,
    )

    conn = get_conn(cfg)
    cur = conn.cursor()

    try:
        print("Inserting bancos...")
        banco_ids = insert_bancos(cur, faker, args.bancos)
        conn.commit()

        print("Inserting usuarios...")
        usuario_ids = insert_usuarios(cur, faker, args.usuarios)
        conn.commit()

        print("Inserting empresas...")
        empresa_ids = insert_empresas(cur, faker, args.empresas)
        conn.commit()
        print(f"[INFO] IDs de empresas gerados: {empresa_ids[:10]} ... total: {len(empresa_ids)}")

        print("Inserting contas...")
        if not banco_ids:
            raise RuntimeError("Nenhum banco foi inserido (banco_ids vazio). Verifique a tabela banco.")
        conta_ids = insert_contas(cur, faker, args.contas, banco_ids)
        conn.commit()

        print("Inserting movimentacoes...")
        _ = insert_movimentacoes(cur, args.movimentacoes, conta_ids)
        conn.commit()

        print("Inserting pagar...")
        _ = insert_pagar(cur, faker, args.pagar, empresa_ids, conta_ids)
        conn.commit()

        print("Inserting receber...")
        _ = insert_receber(cur, faker, args.receber, empresa_ids, conta_ids)

        conn.commit()
        print("Seed completed successfully.")
    except Exception as e:
        conn.rollback()
        raise
    finally:
        cur.close()
        conn.close()


if __name__ == "__main__":
    main()
