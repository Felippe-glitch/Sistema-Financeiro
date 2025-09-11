package com.example.appfinance.Services;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Usuario;
import com.example.appfinance.Repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

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

    public Usuario getUsuario(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! id: " + id));
    }

}
