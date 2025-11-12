package appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.Models.Receber;

public interface ReceberRepository extends JpaRepository<Receber, Long>{
    
    @Procedure(name = "getReceber")
    Receber getReceber(@Param("id_receber") Long id_receber);


}
