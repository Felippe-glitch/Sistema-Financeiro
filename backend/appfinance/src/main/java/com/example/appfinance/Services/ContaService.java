package com.example.appfinance.Services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Conta;
import com.example.appfinance.Repository.ContaRepository;

@Service
public class ContaService {
    private ContaRepository contaRepository;

    @Transactional
    public Conta atualizarSaldo(Long idConta, BigDecimal valor, boolean isEntrada) {
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrado com ID: " +
                        idConta));

        BigDecimal saldoAtual = conta.getSaldo() != null ? conta.getSaldo() : BigDecimal.ZERO;

        if (isEntrada) {
            conta.setSaldo(saldoAtual.add(valor));
        } else {
            conta.setSaldo(saldoAtual.subtract(valor));
        }

        return contaRepository.save(conta);
    }

    // CRUD PARA CONTA
    @Transactional
    public Conta createConta(Conta conta) {
        conta.setIdConta(null);
        try {
            conta = this.contaRepository.save(conta);
            return conta;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar salvar conta : " + e.getMessage());
        }
    }

    @Transactional
    public Conta updateConta(Conta conta) {
        Conta newConta = contaRepository.findById(conta.getIdConta())
                .orElseThrow(() -> new RuntimeException("Banco não encontrado" + conta.getIdConta()));

        newConta.setAgencia(conta.getAgencia());
        newConta.setConta(conta.getConta());
        newConta.setDvConta(conta.getDvConta());
        newConta.setStatusConta(conta.getStatusConta());
        newConta.setTipoConta(conta.getTipoConta());
        newConta.setSaldo(conta.getSaldo());
        newConta.setFkBanco(conta.getFkBanco());
        newConta = contaRepository.save(newConta);
        return newConta;
    }

    @Transactional
    public void deleteConta(Long id) {
        try {
            contaRepository.deleteById(id);
            System.out.println("Banco deletado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar banco: " + e.getMessage());
        }
    }

    @Transactional
    public Page<Conta> getContas(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return contaRepository.findAll(pageable);
    }
}
