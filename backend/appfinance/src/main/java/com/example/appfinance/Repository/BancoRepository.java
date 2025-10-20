package com.example.appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.appfinance.Models.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    // PROCEDURE GET ALL DE BANCOS E CONTAS ASSOCIADAS
    @Procedure(name = "visualizarBancos")
    List<Banco> getAllBancos();
}
