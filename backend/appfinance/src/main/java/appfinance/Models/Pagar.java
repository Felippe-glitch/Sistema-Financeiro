package appfinance.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Pagar.TABLE_NAME)
public class Pagar {
    public static final String TABLE_NAME = "Pagar";

    public interface CreatePagar{}
    public interface UpdatePagar{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPagar")
    private Long idContaPagar;

    @Column(name = "valorPagar", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorPagar;

    @Column(name = "dataVencimento", nullable = false, length = 10)
    @NotNull
    private LocalDateTime dataVencimento;

    @Column(name = "dataEmissao", nullable = false, length = 10)
    @NotNull
    private LocalDateTime dataEmissao;

    @Column(name = "dataPagamento", nullable = true, length = 10)
    private LocalDateTime dataPag;

    @Column(name = "descricaoPagar", length = 255)
    private String descricaoPagar;

    @Column(name = "usuario", nullable = false, length = 50)
    @NotBlank
    private String usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fkEmpresa", referencedColumnName = "idEmpresa")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fkConta", referencedColumnName = "idConta")
    private Conta conta;
}
