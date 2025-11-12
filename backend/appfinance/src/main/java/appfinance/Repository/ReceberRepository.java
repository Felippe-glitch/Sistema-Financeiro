package appfinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.Models.Receber;

public interface ReceberRepository extends JpaRepository<Receber, Long>{
    
    @Procedure(procedureName = "getReceber")
    Receber getReceber(@Param("id_receber") Long id_receber);

    @Procedure(procedureName = "receberPorPeriodo")
    List<Receber> getReceberPeriodo(@Param("emissao") LocalDate emissao, @Param("vencimento") LocalDate vencimento);

}
