package com.example.appfinance.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appfinance.Models.Banco;
import com.example.appfinance.Repository.BancoRepository;

@Service
public class BancoService {
    private BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    // LISTAR TODOS OS BANCOS CADASTRADOS
    public List<Banco> getAllBancos() {
        return bancoRepository.findAll();
    }
}
