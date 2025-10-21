package appfinance.Models;

import java.math.BigDecimal;

import appfinance.Models.ENUM.TipoConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Conta.TABLE_NAME)
public class Conta {

    public static final String TABLE_NAME = "Conta";

    public interface CreateConta{}
    public interface UpdateConta{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConta")
    private Long idConta;

    @Column(name = "agencia", nullable = false, length = 10)
    @NotBlank
    private String agencia;

    @Column(name = "conta", nullable = false, length = 10)
    @NotBlank
    private String conta;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "tipoConta", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoConta tipoConta;

    @Column(name = "statusConta", nullable = false)
    @NotNull
    private int statusConta;

    @Column(name = "dvConta", nullable = false)
    @NotNull
    private int dvConta;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "fkBanco", referencedColumnName = "idBanco", nullable = false)
    private Banco fkBanco;
}
