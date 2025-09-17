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
    @Column(name = "id_receber")
    private Long idReceber;

    @Column(name = "valor_receber", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorReceber;

    @Column(name = "data_vencimento", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataVencimento;

    @Column(name = "data_emissao", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataEmissao;

    @Column(name = "descricao_receber", length = 255)
    private String descricaoReceber;

    @Column(name = "status_receber", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDuplicata statusReceber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_banco", referencedColumnName = "id_banco")
    private Banco fkBanco;
}
