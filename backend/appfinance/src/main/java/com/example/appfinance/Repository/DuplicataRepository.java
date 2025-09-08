package com.example.appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appfinance.Models.Duplicata;

@Repository
public interface DuplicataRepository extends JpaRepository<Duplicata, Long> {

    // LISTAR DUPLICATAS POR ID DO USUÁRIO
    List<Duplicata> findByUsuario_IdUsuario(Long idUsuario);
}
