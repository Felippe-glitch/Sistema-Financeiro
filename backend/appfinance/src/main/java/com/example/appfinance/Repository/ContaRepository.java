package com.example.appfinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appfinance.Models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

}
