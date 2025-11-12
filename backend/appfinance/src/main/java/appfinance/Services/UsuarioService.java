package appfinance.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appfinance.Models.Movimentacao;
import appfinance.Models.Usuario;
import appfinance.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    public Object getAuditoriaDuplicatas;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CRUD PARA USUARIO
    @Transactional
    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmailUsuario(usuario.getEmailUsuario())) {
            throw new RuntimeErrorException(null, "Email já cadastrado: " + usuario.getEmailUsuario());
        } else {
            usuario.setIdUsuario(null);
            usuario.setSenhaHashUsuario(passwordEncoder.encode(usuario.getSenhaHashUsuario()));
            usuario = this.usuarioRepository.save(usuario);
            return usuario;
        }
    }

    @Transactional
    public Usuario updateUsuario(Usuario usuario) {
        Usuario newUsuario = usuarioRepository.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuario.getIdUsuario()));

        newUsuario.setNomeUsuario(usuario.getNomeUsuario());
        newUsuario.setEmailUsuario(usuario.getEmailUsuario());
        if (usuario.getSenhaHashUsuario() != null && !usuario.getSenhaHashUsuario().isBlank()) {
            newUsuario.setSenhaHashUsuario(passwordEncoder.encode(usuario.getSenhaHashUsuario()));
        }

        newUsuario = usuarioRepository.save(newUsuario);

        return newUsuario;
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public Usuario getUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.getUsuario(id);
        return usuario.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! id: " + id));
    }

    @Transactional
    public List<Object[]> getAuditoriaDuplicatas(String nome, int tipo, LocalDate emissao, LocalDate vencimento){
        return usuarioRepository.auditoriaDuplicatas(nome, tipo, emissao, vencimento);
    }

    @Transactional
    public List<Movimentacao> getAuditoriaMov(String nome, LocalDate data){
        return usuarioRepository.auditoriaMov(nome, data);
    }

}
