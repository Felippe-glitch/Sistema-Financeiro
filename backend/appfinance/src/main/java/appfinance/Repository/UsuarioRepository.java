package appfinance.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import appfinance.Models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailUsuario(String emailUsuario);

    @Procedure(name = "getUsuario")
    Optional<Usuario> getUsuario(@Param("id_usuario") Long id);
}
