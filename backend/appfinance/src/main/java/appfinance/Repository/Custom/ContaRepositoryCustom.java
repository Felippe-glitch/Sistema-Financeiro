package appfinance.Repository.Custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import appfinance.DTO.ExtratoContaDTO;

@Repository
public class ContaRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public ContaRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ExtratoContaDTO extratoContaById(Long idConta) {
        String sql = "EXEC extratoConta @id_conta = ?";
        return jdbcTemplate.queryForObject(
        sql,
        (rs, rowNum) -> new ExtratoContaDTO(
            rs.getString("Numero_Conta"),
            rs.getString("Nome_Banco"),
            rs.getBigDecimal("Total_Pago"),
            rs.getBigDecimal("Total_Recebido"),
            rs.getBigDecimal("saldo")
        ),
        idConta 
    );
    }
}

