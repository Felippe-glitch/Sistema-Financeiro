# FinanceiroDB Data Seeder (SQL Server)

This script seeds your FinanceiroDB (SQL Server) with large, realistic datasets using Faker.

It populates the following tables:
- Banco
- Conta
- Empresa
- Usuario
- Movimentacao
- Pagar
- Receber

The data includes varied scenarios: overdue and paid bills, future/near/past due dates, mixed payment methods (PIX, cartão, boleto, etc.), active/inactive accounts, and both pessoa física/jurídica companies with CPF/CNPJ.

## Requirements
- Windows with SQL Server reachable at the configured host
- Python 3.10+
- ODBC driver for SQL Server (ODBC Driver 17 for SQL Server recommended)

## Install dependencies
```powershell
# From the repository root
python -m venv .venv; .\.venv\Scripts\Activate.ps1; pip install -r .\data_seeder\requirements.txt
```

## Configure connection
Defaults are read from your Spring Boot config values:
- server: DESKTOP-H4LAA9J
- database: FinanceiroDB
- username: sa
- password: 1234
- driver: ODBC Driver 17 for SQL Server

You can override via flags or env vars (SQLSERVER_HOST, SQLSERVER_DB, SQLSERVER_USER, SQLSERVER_PASSWORD, SQLSERVER_DRIVER).

## Run
```powershell
# Example: moderate volume
python .\data_seeder\seed_sqlserver.py --empresas 1000 --contas 300 --movimentacoes 8000 --pagar 5000 --receber 5000

# Heavier load example
python .\data_seeder\seed_sqlserver.py --bancos 20 --usuarios 1000 --empresas 5000 --contas 1500 --movimentacoes 40000 --pagar 25000 --receber 25000
```

Tip: The script commits after each phase to keep the transaction log manageable for large runs.

## Notes on schema alignment
- Empresa.tipoEmpresa and Empresa.tipoPessoa are integers in your model (0/1/2). The seeder uses these integer codes.
- Conta.tipoConta is persisted as an enum string (CORRENTE/POUPANCA).
- Movimentacao.forma_pagamento uses enum strings like PIX, BOLETO, etc.
- Date/time fields are populated with LocalDateTime-compatible timestamps.

If your database has additional constraints or triggers, adjust the insert statements accordingly in `seed_sqlserver.py`.
