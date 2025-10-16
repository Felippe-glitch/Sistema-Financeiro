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
@AllArgsConstructor
@Setter
@Getter
@Table(name = Receber.TABLE_NAME)
public class Receber {
    public static final String TABLE_NAME = "receber";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceber")
    private Long idReceber;

    @Column(name = "valorReceber", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorReceber;

    @Column(name = "dataVencimento", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataVencimento;

    @Column(name = "dataEmissao", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataEmissao;

    @Column(name = "descricaoReceber", length = 255)
    private String descricaoReceber;

    @Column(name = "statusReceber", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDuplicata statusReceber;

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
