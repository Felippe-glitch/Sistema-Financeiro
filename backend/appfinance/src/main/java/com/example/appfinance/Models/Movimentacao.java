package com.example.appfinance.Models;

import java.time.LocalDateTime;

import com.example.appfinance.Models.ENUM.FormaPagamento;
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "tipo_duplicata", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDuplicata TipoDuplicata;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_banco", referencedColumnName = "id_banco")
    private Banco banco;
}
