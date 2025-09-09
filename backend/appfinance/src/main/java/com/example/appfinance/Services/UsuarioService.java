package com.example.appfinance.Services;

import javax.management.RuntimeErrorException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Usuario;
import com.example.appfinance.Repository.UsuarioRepository;

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
    public Usuario createUsuario(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmailUsuario())){
            throw new RuntimeErrorException(null, "Email já cadastrado: " + usuario.getEmailUsuario());
        } else{
            usuario.setIdUsuario(null);
            usuario.setSenhaHashUsuario(passwordEncoder.encode(usuario.getSenhaHashUsuario()));
            usuario = this.usuarioRepository.save(usuario);
            return usuario;
        }
    }

    @Transactional
    public Usuario updateUsuario(Usuario usuario){
        Usuario newUsuario = usuarioRepository.findById(usuario.getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuario.getIdUsuario()));

        newUsuario.setNomeUsuario(usuario.getNomeUsuario());
        newUsuario.setEmailUsuario(usuario.getEmailUsuario());
        if (usuario.getSenhaHashUsuario() != null && !usuario.getSenhaHashUsuario().isBlank()) {
            newUsuario.setSenhaHashUsuario(passwordEncoder.encode(usuario.getSenhaHashUsuario()));
        }

        newUsuario = usuarioRepository.save(newUsuario);

        return newUsuario;
    }

    @Transactional
    public Usuario deleteUsuario(Long id){
        try {
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
            usuarioRepository.deleteById(id);
            return usuario;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
