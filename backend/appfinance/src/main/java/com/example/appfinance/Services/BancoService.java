package com.example.appfinance.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Banco;
import com.example.appfinance.Repository.BancoRepository;

@Service
public class BancoService {
    private BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    // CRUD PARA BANCO
    @Transactional
    public Banco createBanco(Banco banco) {
        banco.setIdBanco(null);
        banco = this.bancoRepository.save(banco);
        return banco;
    }

    @Transactional
    public Banco updateBanco(Banco banco) {
        Banco newBanco = bancoRepository.findById(banco.getIdBanco())
                .orElseThrow(() -> new RuntimeException("Banco não encontrado" + banco.getIdBanco()));

        newBanco.setNomeBanco(banco.getNomeBanco());
        newBanco.setAgencia(banco.getAgencia());
        newBanco.setConta(banco.getConta());
        newBanco = bancoRepository.save(newBanco);
        return newBanco;
    }

    @Transactional
    public void deleteBanco(Long id) {
        try {
            bancoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar banco: " + e.getMessage());
        }
    }

    @Transactional
    public Banco atualizarSaldo(Long idBanco, BigDecimal valor, boolean isEntrada) {
        Banco banco = bancoRepository.findById(idBanco)
                .orElseThrow(() -> new RuntimeException("Banco não encontrado com ID: " + idBanco));

        BigDecimal saldoAtual = banco.getSaldo() != null ? banco.getSaldo() : BigDecimal.ZERO;

        if (isEntrada) {
            banco.setSaldo(saldoAtual.add(valor));
        } else {
            banco.setSaldo(saldoAtual.subtract(valor));
        }

        return bancoRepository.save(banco);
    }

    // LISTAR TODOS OS BANCOS CADASTRADOS
    public List<Banco> getAllBancos() {
        return bancoRepository.findAll();
    }
}
