package com.example.appfinance.DTO;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoBancarioDTO {
    private String conta;
    private String nomeBanco;
    private BigDecimal totalPagar;
    private BigDecimal totalReceber;
    private BigDecimal saldo;
}
