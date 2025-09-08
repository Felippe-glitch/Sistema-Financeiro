package com.example.appfinance.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appfinance.Models.Empresa;
import com.example.appfinance.Models.ENUM.TipoEmpresa;
import com.example.appfinance.Repository.EmpresaRepository;

@Service
public class EmpresaService {
    private EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    // LISTAR TODAS AS EMPRESAS
    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    // LISTAR TODOS OS CLIENTES
    public List<Empresa> getClientes(){
        return empresaRepository.findByTipoEmpresa(TipoEmpresa.CLIENTE);
    }

    //LISTAR TODOS OS FORNECEDORES
    public List<Empresa> getFornecedor(){
        return empresaRepository.findByTipoEmpresa(TipoEmpresa.FORNECEDOR);
    }
}
