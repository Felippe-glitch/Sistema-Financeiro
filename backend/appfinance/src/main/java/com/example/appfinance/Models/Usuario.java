package com.example.appfinance.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Usuario.TABLE_NAME)
public class Usuario {

    public static final String TABLE_NAME = "Usuario";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(name = "nome_usuario", nullable = false, length = 50)
    @NotBlank
    private String nomeUsuario;

    @Column(name = "login_usuario", nullable = false, length = 100, unique = true)
    @NotBlank
    @Size(min = 5, max = 100)
    private String loginUsuario;

    @Column(name = "senha_usuario", nullable = false)
    @NotBlank
    @Size(min = 8, max = 100)
    private String senhaHashUsuario;

    // Construtores, getters e setters

    public Usuario() {}

    public Usuario(Long idUsuario, String nomeUsuario, String loginUsuario, String senhaUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaHashUsuario = senhaUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenhaHashUsuario() {
        return senhaHashUsuario;
    }

    public void setSenhaHashUsuario(String senhaHashUsuario) {
        this.senhaHashUsuario = senhaHashUsuario;
    }
}
