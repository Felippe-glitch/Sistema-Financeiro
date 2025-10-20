package com.example.appfinance.Models;

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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = Receber.TABLE_NAME)
public class Receber {
    public static final String TABLE_NAME = "receber";

    public interface CreateReceber{}
    public interface UpdateReceber{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceber")
    private Long idReceber;

    @Column(name = "valorReceber", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorReceber;

    @Column(name = "dataVencimento", nullable = false, length = 10)
    @NotNull
    private LocalDateTime dataVencimento;

    @Column(name = "dataEmissao", nullable = false, length = 10)
    @NotNull
    private LocalDateTime dataEmissao;

    @Column(name = "dataRecebimento", nullable = true, length = 10)
    private LocalDateTime dataRec;

    @Column(name = "descricaoReceber", length = 255)
    private String descricaoReceber;

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
