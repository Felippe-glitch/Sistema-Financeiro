package appfinance.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import appfinance.Models.Pagar;

public interface PagarRepository extends JpaRepository<Pagar, Long> {
    @Procedure
    Pagar getPagar(@Param("id_pagar") Long id_pagar);
}
