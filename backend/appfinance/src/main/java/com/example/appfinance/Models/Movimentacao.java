package com.example.appfinance.Models;

import java.time.LocalDateTime;

import com.example.appfinance.Models.ENUM.FormaPagamento;

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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = Movimentacao.TABLE_NAME)
public class Movimentacao {
    public static final String TABLE_NAME = "Movimentacao";

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_duplicata", referencedColumnName = "id_duplicata")
    private Duplicata duplicata;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_banco", referencedColumnName = "id_banco")
    private Banco banco;

    // Construtores, getters e setters

    public Movimentacao() {
    }

    public Movimentacao(Long idMovimentacao, LocalDateTime dataRegistroMovimentacao, FormaPagamento formaPagamento, Duplicata duplicata, Usuario usuario) {
        this.idMovimentacao = idMovimentacao;
        this.dataRegistroMovimentacao = dataRegistroMovimentacao;
        this.formaPagamento = formaPagamento;
        this.duplicata = duplicata;
        this.usuario = usuario;
    }

    public Long getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(Long idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public LocalDateTime getDataRegistroMovimentacao() {
        return dataRegistroMovimentacao;
    }

    public void setDataRegistroMovimentacao(LocalDateTime dataRegistroMovimentacao) {
        this.dataRegistroMovimentacao = dataRegistroMovimentacao;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Duplicata getDuplicata() {
        return duplicata;
    }

    public void setDuplicata(Duplicata duplicata) {
        this.duplicata = duplicata;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean getTipo() {
        throw new UnsupportedOperationException("Unimplemented method 'getTipo'");
    }
}
