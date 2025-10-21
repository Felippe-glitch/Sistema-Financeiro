package appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.Models.Receber;

public interface ReceberRepository extends JpaRepository<Receber, Long>{
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
    List<Receber> auditoriaReceberPorUsuario(@Param("NOME") String nome, @Param("TIPO") int tipo);
}
