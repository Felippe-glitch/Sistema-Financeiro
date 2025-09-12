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

@Entity
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

    // Construtores, getters e setters
    public Receber() {}

    public Receber(Long idReceber, BigDecimal valorReceber, LocalDateTime dataVencimento,
                   LocalDateTime dataEmissao, String descricaoReceber, StatusDuplicata statusReceber,
                   Usuario usuario, Empresa empresa, Banco fkBanco) {
        this.idReceber = idReceber;
        this.valorReceber = valorReceber;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = dataEmissao;
        this.descricaoReceber = descricaoReceber;
        this.statusReceber = statusReceber;
        this.usuario = usuario;
        this.empresa = empresa;
        this.fkBanco = fkBanco;
    }

    public Long getIdReceber() {
        return idReceber;
    }

    public void setIdReceber(Long idReceber) {
        this.idReceber = idReceber;
    }

    public Banco getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(Banco idBanco) {
        this.fkBanco = idBanco;
    }

    public BigDecimal getValorReceber() {
        return valorReceber;
    }

    public void setValorReceber(BigDecimal valorReceber) {
        this.valorReceber = valorReceber;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDescricaoReceber() {
        return descricaoReceber;
    }

    public void setDescricaoReceber(String descricaoReceber) {
        this.descricaoReceber = descricaoReceber;
    }

    public StatusDuplicata getStatusReceber() {
        return statusReceber;
    }

    public void setStatusReceber(StatusDuplicata statusReceber) {
        this.statusReceber = statusReceber;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
}
