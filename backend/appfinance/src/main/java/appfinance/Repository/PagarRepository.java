package appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.Models.Pagar;

public interface PagarRepository extends JpaRepository<Pagar, Long> {
    // PROCEDURE PARA AUDITORIA DE LANÇAMENTO POR USUÁRIO
    /*
     * -> RECEBE:
     * 
     * - Nome do Usuário, tipo (0 = pagar, 1 = receber)
     * 
     * -> RETORNA: 
     * 
     * ID, Data_Emissao, Data_Vencimento, Descricao, Status, Nome_Empresa, Num_Conta  
     * 
     */
    @Procedure(name = "auditoriaDuplicatas")
    List<Pagar> auditoriaDuplicatas(@Param("NOME") String nome, @Param("TIPO") int tipo);

    @Procedure
    Pagar getPagar(@Param("id_pagar") Long id_pagar);
}
