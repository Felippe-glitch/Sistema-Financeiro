package appfinance.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoPeriodoDTO {
    private String tipo;       // "Pago" ou "Recebido"
    private BigDecimal valor;  
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
