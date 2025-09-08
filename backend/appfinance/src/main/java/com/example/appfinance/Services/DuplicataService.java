package com.example.appfinance.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appfinance.Models.Duplicata;
import com.example.appfinance.Repository.DuplicataRepository;

@Service
public class DuplicataService {
    private DuplicataRepository duplicataRepository;

    public DuplicataService(DuplicataRepository duplicataRepository) {
        this.duplicataRepository = duplicataRepository;
    }

    // LISTAR DUPLICATAS PELO ID DO USUÁRIO
    public List<Duplicata> getDuplicataByUsuarioId(Long usuarioId) {
        return duplicataRepository.findByUsuario_IdUsuario(usuarioId);
    }
}
