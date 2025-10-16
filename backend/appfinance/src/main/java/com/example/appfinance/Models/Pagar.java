package com.example.appfinance.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.appfinance.Models.ENUM.StatusDuplicata;

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
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = Pagar.TABLE_NAME)
public class Pagar {
    public static final String TABLE_NAME = "Pagar";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPagar")
    private Long idContaPagar;

    @Column(name = "valorPagar", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorPagar;

    @Column(name = "dataVencimento", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataVencimento;

    @Column(name = "dataEmissao", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataEmissao;

    @Column(name = "descricaoPagar", length = 255)
    private String descricaoPagar;

    @Column(name = "statusReceber", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDuplicata statusPagar;

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
