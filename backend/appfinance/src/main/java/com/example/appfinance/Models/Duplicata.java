package com.example.appfinance.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.appfinance.Models.ENUM.StatusDuplicata;
import com.example.appfinance.Models.ENUM.TipoDuplicata;

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
@Table(name = Duplicata.TABLE_NAME)
public class Duplicata {
    public static final String TABLE_NAME = "Duplicata";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_duplicata")
    private Long idDuplicata;

    @Column(name = "tipo_duplicata", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDuplicata tipoDuplicata;

    @Column(name = "valor_duplicata", precision = 10, scale = 2, nullable = false, length = 10)
    @NotNull
    private BigDecimal valorDuplicata;

    @Column(name = "data_vencimento", nullable = false, length = 10)
    @NotBlank
    private LocalDate dataVencimento;

    @Column(name = "data_emissao", nullable = false, length = 10)
    @NotBlank
    private LocalDate dataEmissao;

    @Column(name = "descricao_duplicata", length = 255)
    private String descricaoDuplicata;

    @Column(name = "status_duplicata", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDuplicata statusDuplicata;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa")
    private Empresa empresa;

    // Construtores, getters e setters
    public Duplicata() {}

    public Duplicata(Long idDuplicata, TipoDuplicata tipoDuplicata, BigDecimal valorDuplicata, LocalDate dataVencimento,
            LocalDate dataEmissao, String descricaoDuplicata, StatusDuplicata statusDuplicata, Usuario usuario,
            Empresa empresa) {
        this.idDuplicata = idDuplicata;
        this.tipoDuplicata = tipoDuplicata;
        this.valorDuplicata = valorDuplicata;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = dataEmissao;
        this.descricaoDuplicata = descricaoDuplicata;
        this.statusDuplicata = statusDuplicata;
        this.usuario = usuario;
        this.empresa = empresa;
    }

    public Long getIdDuplicata() {
        return idDuplicata;
    }

    public void setIdDuplicata(Long idDuplicata) {
        this.idDuplicata = idDuplicata;
    }

    public TipoDuplicata getTipoDuplicata() {
        return tipoDuplicata;
    }

    public void setTipoDuplicata(TipoDuplicata tipoDuplicata) {
        this.tipoDuplicata = tipoDuplicata;
    }

    public BigDecimal getValorDuplicata() {
        return valorDuplicata;
    }

    public void setValorDuplicata(BigDecimal valorDuplicata) {
        this.valorDuplicata = valorDuplicata;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDescricaoDuplicata() {
        return descricaoDuplicata;
    }

    public void setDescricaoDuplicata(String descricaoDuplicata) {
        this.descricaoDuplicata = descricaoDuplicata;
    }

    public StatusDuplicata getStatusDuplicata() {
        return statusDuplicata;
    }

    public void setStatusDuplicata(StatusDuplicata statusDuplicata) {
        this.statusDuplicata = statusDuplicata;
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
