package com.example.appfinance.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Duplicata;
import com.example.appfinance.Repository.DuplicataRepository;

@Service
public class DuplicataService {
    private DuplicataRepository duplicataRepository;

    public DuplicataService(DuplicataRepository duplicataRepository) {
        this.duplicataRepository = duplicataRepository;
    }

    //CRUD BÁSICO PARA DUPLICATA

    @Transactional
    public Duplicata createDuplicata(Duplicata duplicata){
        duplicata.setIdDuplicata((null));
        duplicata = duplicataRepository.save(duplicata);
        return duplicata;
    }

    @Transactional
    public Duplicata updateDuplicata(Duplicata duplicata){
        Duplicata newDuplicata = duplicataRepository.findById(duplicata.getIdDuplicata()).orElseThrow(() -> new RuntimeException("Duplicata não encontrado: " + duplicata.getIdDuplicata()));

        newDuplicata.setDataEmissao(duplicata.getDataEmissao());
        newDuplicata.setDataVencimento(duplicata.getDataVencimento());
        newDuplicata.setValorDuplicata(duplicata.getValorDuplicata());
        newDuplicata.setDescricaoDuplicata(duplicata.getDescricaoDuplicata());
        newDuplicata.setStatusDuplicata(duplicata.getStatusDuplicata());
        newDuplicata.setEmpresa(duplicata.getEmpresa());
        newDuplicata.setUsuario(duplicata.getUsuario());

        newDuplicata = duplicataRepository.save(newDuplicata);

        return newDuplicata;
    }

    @Transactional
    public Duplicata deleteDuplicata(Long id){
        try {
            Duplicata duplicata = duplicataRepository.findById(id).orElseThrow(() -> new RuntimeException("Duplicata não encontrada: " + id));
            duplicataRepository.deleteById(id);
            return duplicata;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar duplicata: " + e.getMessage());
        }
    }
    
    // LISTAR DUPLICATAS PELO ID DO USUÁRIO
    public List<Duplicata> getDuplicataByUsuarioId(Long usuarioId) {
        return duplicataRepository.findByUsuario_IdUsuario(usuarioId);
    }

    // LISTAR DUPLICATAS PELO ID DA EMPRESA
    public List<Duplicata> getDuplicatasByEmpresaId(Long empresaId){
        return duplicataRepository.findByEmpresa_IdEmpresa(empresaId);
    }
}
