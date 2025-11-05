package appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import appfinance.Models.Empresa;
import appfinance.Models.Pagar;
import appfinance.Models.Receber;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByTipoEmpresa(int tipoEmpresa);

    boolean existsByCpfCnpj(String cpfCnpj);

    // PROCEDURE PARA VISUALIZAR EMPRESA PELO ID
    /*
     * -> RETORNA:
     * 
     * ID, CPF/CNPJ, Razao_Social, Tipo_Empresa, Tipo_Pessoa, Pais, Estado, CEP, Bairro, Rua,  
     * Numero, Telefone, Telefone, Email
     * 
     */
    @Procedure(name = "getEmpresa")
    Empresa getEmpresa(@Param("id_empresa") Long id_empresa);

    @Procedure(name = "extratoFornecedor")
    List<Pagar> pagarByFornecedor(@Param("id_empresa") Long idEmpresa);

    @Procedure(name = "extratoCliente")
    List<Receber> receberByCliente(@Param("id_empresa") Long idEmpresa);
}
