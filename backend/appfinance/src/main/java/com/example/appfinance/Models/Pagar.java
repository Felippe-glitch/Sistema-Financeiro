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
@Table(name = Pagar.TABLE_NAME)
public class Pagar {
    public static final String TABLE_NAME = "pagar";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagar")
    private Long idContaPagar;

    @Column(name = "valor_pagar", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorPagar;

    @Column(name = "data_vencimento", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataVencimento;

    @Column(name = "data_emissao", nullable = false, length = 10)
    @NotBlank
    private LocalDateTime dataEmissao;

    @Column(name = "descricao_pagar", length = 255)
    private String descricaoPagar;

    @Column(name = "status_receber", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDuplicata statusPagar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_empresa", referencedColumnName = "id_empresa")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_banco", referencedColumnName = "id_banco")
    private Banco fkBanco;

    // Construtores, getters e setters
    public Pagar() {}

    public Pagar(Long idDuplicata, BigDecimal valorPagar, LocalDateTime dataVencimento,
            LocalDateTime dataEmissao, String descricaoPagar, StatusDuplicata statusPagar, Usuario usuario,
            Empresa empresa, Banco fkBanco) {
        this.idContaPagar = idDuplicata;
        this.valorPagar = valorPagar;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = dataEmissao;
        this.descricaoPagar = descricaoPagar;
        this.statusPagar = statusPagar;
        this.usuario = usuario;
        this.empresa = empresa;
        this.fkBanco = fkBanco;
    }

    public Long getIdDuplicata() {
        return idContaPagar;
    }

    public void setIdDuplicata(Long idDuplicata) {
        this.idContaPagar = idDuplicata;
    }

    public Banco getFkBanco() {
        return fkBanco;
    }

    public void setFkBanco(Banco idBanco) {
        this.fkBanco = idBanco;
    }

    public BigDecimal getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(BigDecimal valorPagar) {
        this.valorPagar = valorPagar;
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

    public String getDescricaoPagar() {
        return descricaoPagar;
    }

    public void setDescricaoPagar(String descricaoPagar) {
        this.descricaoPagar = descricaoPagar;
    }

    public StatusDuplicata getStatusPagar() {
        return statusPagar;
    }

    public void setStatusPagar(StatusDuplicata statusPagar) {
        this.statusPagar = statusPagar;
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
