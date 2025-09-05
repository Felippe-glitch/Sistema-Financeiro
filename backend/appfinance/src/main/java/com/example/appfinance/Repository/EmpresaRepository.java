package com.example.appfinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appfinance.Models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {}
