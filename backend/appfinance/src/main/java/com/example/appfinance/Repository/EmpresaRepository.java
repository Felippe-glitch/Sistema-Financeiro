package com.example.appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.appfinance.Models.Empresa;
import com.example.appfinance.Models.ENUM.TipoEmpresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByTipoEmpresa(TipoEmpresa tipoEmpresa);

    boolean existsByCpfCnpj(String cpfCnpj);

    @Procedure(name = "visualizarEmpresa")
    void empresaById(@Param("empId") Long empId);
}
