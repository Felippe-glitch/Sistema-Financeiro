package com.example.appfinance.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = Banco.TABLE_NAME)
public class Banco {
    public static final String TABLE_NAME = "Banco";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco")
    private Long idBanco;

    @Column(name = "nome_banco", nullable = false, length = 100)
    @NotBlank

    private String nomeBanco;

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

    // Construtores, getters e setters

    public Banco() {
    }

    public Banco(Long idBanco, String nomeBanco, String agencia, String conta) {
        this.idBanco = idBanco;
        this.nomeBanco = nomeBanco;
        this.agencia = agencia;
        this.conta = conta;
    }

    public Long getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Long idBanco) {
        this.idBanco = idBanco;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }
}
