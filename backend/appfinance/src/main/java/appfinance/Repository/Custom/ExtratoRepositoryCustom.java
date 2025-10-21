package appfinance.Repository.Custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import appfinance.DTO.ExtratoDiarioDTO;

import java.util.List;

@Repository
public class ExtratoRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public ExtratoRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ExtratoDiarioDTO> extratoDiario() {
        String sql = "EXEC extratoDiario";

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new ExtratoDiarioDTO(
                rs.getString("Tipo"),
                rs.getBigDecimal("Valor")
            )
        );
    }
}
