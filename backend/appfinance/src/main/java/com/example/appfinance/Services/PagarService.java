package com.example.appfinance.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.appfinance.Models.Pagar;
import com.example.appfinance.Repository.PagarRepository;

import jakarta.transaction.Transactional;

@Service
public class PagarService {

    @Autowired
    private PagarRepository pagarRepository;

    @Transactional
    public Pagar createPagar(Pagar pagar) {
        pagar.setIdContaPagar((null));
        try {
            Pagar duplicata = pagarRepository.save(pagar);
            return duplicata;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar conta: " + e.getMessage());
        }
    }

    @Transactional
    public Pagar updatePagar(Pagar pagar) {
        Pagar newDuplicata = pagarRepository.findById(pagar.getIdContaPagar())
                .orElseThrow(() -> new RuntimeException("Duplicata não encontrado: " + pagar.getIdContaPagar()));

        newDuplicata.setDataEmissao(pagar.getDataEmissao());
        newDuplicata.setDataVencimento(pagar.getDataVencimento());
        newDuplicata.setValorPagar(pagar.getValorPagar());
        newDuplicata.setDescricaoPagar(pagar.getDescricaoPagar());
        newDuplicata.setEmpresa(pagar.getEmpresa());
        newDuplicata.setUsuario(pagar.getUsuario());
        newDuplicata.setConta(pagar.getConta());
        newDuplicata.setDataPag(null);

        newDuplicata = pagarRepository.save(newDuplicata);

        return newDuplicata;
    }

    @Transactional
    public Pagar deletePagar(Long id) {
        try {
            Pagar duplicata = pagarRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Duplicata não encontrada: " + id));
            pagarRepository.deleteById(id);
            return duplicata;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar duplicata: " + e.getMessage());
        }
    }

    @Transactional
    public Page<Pagar> getContasPagar(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return pagarRepository.findAll(pageable);
    }
}
