package com.example.appfinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.appfinance.Models.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {}
