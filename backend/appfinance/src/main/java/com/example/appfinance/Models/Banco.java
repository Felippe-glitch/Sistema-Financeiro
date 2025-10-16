package com.example.appfinance.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = Banco.TABLE_NAME)
public class Banco {
    public static final String TABLE_NAME = "Banco";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBanco")
    private Long idBanco;

    @Column(name = "nomeBanco", nullable = false, length = 100)
    @NotBlank
    private String nomeBanco;

    
}
