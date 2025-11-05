package appfinance.Repository.Custom;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import appfinance.DTO.ExtratoPeriodoDTO;

@Repository
public class ExtratoPeriodoRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public ExtratoPeriodoRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ExtratoPeriodoDTO> extratoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        String sql = "EXEC extratoPeriodo @DataInicio = ?, @DataFim = ?";

        Timestamp tsInicio = Timestamp.valueOf(dataInicio);
        Timestamp tsFim = Timestamp.valueOf(dataFim);

        return jdbcTemplate.query(
                sql,
                new Object[] { tsInicio, tsFim },
                (rs, rowNum) -> new ExtratoPeriodoDTO(
                        rs.getString("Tipo"),
                        rs.getBigDecimal("Valor"),
                        dataInicio,
                        dataFim));
    }

}
