package appfinance.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appfinance.Models.Empresa;
import appfinance.Repository.EmpresaRepository;

@Service
public class EmpresaService {
    private EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    // CRUD PARA EMPRESA
    @Transactional
    public Empresa saveEmpresa(Empresa empresa) {
        if (empresaRepository.existsByCpfCnpj(empresa.getCpfCnpj())) {
            throw new RuntimeException("Já existe uma empresa cadastrada com este CPF/CNPJ: " + empresa.getCpfCnpj());
        } else{
            empresa.setIdEmpresa(null);
            empresa = this.empresaRepository.save(empresa);
            return empresa;
        }
    }

    @Transactional
    public Empresa updateEmpresa(Empresa empresa) {
        Empresa newEmpresa = empresaRepository.findById(empresa.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada" + empresa.getIdEmpresa()));

        newEmpresa.setRazaoSocial(empresa.getRazaoSocial());
        newEmpresa.setTipoEmpresa(empresa.getTipoEmpresa());
        newEmpresa.setCpfCnpj(empresa.getCpfCnpj());
        newEmpresa.setTipoPessoa(empresa.getTipoPessoa());
        newEmpresa.setNomeFantasia(empresa.getNomeFantasia());
        newEmpresa.setTelefone(empresa.getTelefone());
        newEmpresa.setEmail(empresa.getEmail());
        newEmpresa.setCidadeEmpresa(empresa.getCidadeEmpresa());
        newEmpresa.setRuaEmpresa(empresa.getRuaEmpresa());
        newEmpresa.setNumeroEmpresa(empresa.getNumeroEmpresa());
        newEmpresa.setBairroEmpresa(empresa.getBairroEmpresa());
        newEmpresa.setCepEmpresa(empresa.getCepEmpresa());
        newEmpresa.setEstadoEmpresa(empresa.getEstadoEmpresa());
        newEmpresa.setPaisEmpresa(empresa.getPaisEmpresa());

        newEmpresa = empresaRepository.save(newEmpresa);

        return newEmpresa;
    }

    @Transactional
    public void deleteEmpresa(Long id) {
        try {
            empresaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar empresa: " + e.getMessage());
        }
    }

    @Transactional
    public Page<Empresa> getEmpresas(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return empresaRepository.findAll(pageable);
    }
}
