package appfinance.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import appfinance.Models.Movimentacao;
import appfinance.Models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailUsuario(String emailUsuario);

    @Procedure(name = "getUsuario")
    Optional<Usuario> getUsuario(@Param("id_usuario") Long id);

    @Query(value = "EXEC auditoriaDuplicatas :nome, :tipo, :data_inicio, :data_fim", nativeQuery = true)
    List<Object[]> auditoriaDuplicatas(@Param("nome") String nome, @Param("tipo") int tipo, @Param("data_inicio") LocalDate data_inicio, @Param("data_fim") LocalDate data_fim);

    @Query(value = "EXEC auditoriaMovimentacoes :nome, :data", nativeQuery = true)
    List<Movimentacao> auditoriaMov(@Param("nome") String nome, @Param("data") LocalDate data);
}
