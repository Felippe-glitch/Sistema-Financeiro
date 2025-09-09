package com.example.appfinance.Services;

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
    public Banco createBanco(Banco banco){
        banco.setIdBanco(null);
        banco = this.bancoRepository.save(banco);
        return banco;
    }

    @Transactional
    public Banco updateBanco(Banco banco){
        Banco newBanco = bancoRepository.findById(banco.getIdBanco()).orElseThrow(() -> new RuntimeException("Banco não encontrado" + banco.getIdBanco()));
        
        newBanco.setNomeBanco(banco.getNomeBanco());
        newBanco.setAgencia(banco.getAgencia());
        newBanco.setConta(banco.getConta());
        newBanco = bancoRepository.save(newBanco);
        return newBanco;
    }

    @Transactional
    public void deleteBanco(Long id){
        try{
            bancoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar banco: " + e.getMessage());
        }
    }

    // LISTAR TODOS OS BANCOS CADASTRADOS
    public List<Banco> getAllBancos() {
        return bancoRepository.findAll();
    }
}
