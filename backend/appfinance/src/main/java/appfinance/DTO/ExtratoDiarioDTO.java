package appfinance.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDiarioDTO {

    private String tipo;       // "Pago" ou "Recebido"
    private BigDecimal valor;  

}

