package appfinance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import appfinance.Models.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Procedure(name = "getMovimentacao")
    public Movimentacao getMovimentacao(@Param("id_movimentacao") Long id_movimentacao);
}
