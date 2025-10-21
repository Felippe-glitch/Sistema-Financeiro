package appfinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.DTO.ExtratoContaDTO;
import appfinance.Models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

    // PROCEDURE PARA VISUALIZAR INFORMAÇÕES DE CONTA PELO ID
    /*
     * -> RETORNA: 
     * 
     * ID, Agencia, Num_conta, DV, Saldo, Tipo_Conta, fk_banco
     * 
     */
    @Procedure(name = "visualizarConta")
    Conta visualizarConta(@Param("id_conta") Long id_conta);

    // PROCEDURE PARA EXTRATO DE CONTA
    // Trocar para interface de extrato
    @Procedure(name = "extratoConta")
    ExtratoContaDTO extratoConaById(@Param("id_conta") Long contaId);

}
