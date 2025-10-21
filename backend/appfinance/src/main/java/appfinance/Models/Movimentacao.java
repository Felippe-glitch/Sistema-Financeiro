package appfinance.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import appfinance.Models.ENUM.FormaPagamento;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = Movimentacao.TABLE_NAME)
public class Movimentacao {
    public static final String TABLE_NAME = "Movimentacao";

    public interface CreateMovimentacao {}
    public interface UpdateMovimentacao {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long idMovimentacao;

    @Column(name = "data_registro_movimentacao", nullable = false)
    @NotNull
    private LocalDateTime dataRegistroMovimentacao;

    @Column(name = "forma_pagamento", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "valor_mov", nullable = false)
    @NotNull
    private BigDecimal valor;

    /*
     * 0 -> Pagar
     * 1 -> Receber
     */
    @Column(name = "tipo_duplicata", nullable = false)
    private int tipoDuplicata;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fkConta", referencedColumnName = "idConta", nullable = false)
    private Conta conta;

    @Column(name = "usuario_cad", nullable = false)
    @NotBlank
    private String usuario;
}
