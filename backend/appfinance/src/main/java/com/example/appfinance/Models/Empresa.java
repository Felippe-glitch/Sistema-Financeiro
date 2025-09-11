package com.example.appfinance.Models;

import com.example.appfinance.Models.ENUM.TipoEmpresa;
import com.example.appfinance.Models.ENUM.TipoPessoa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Empresa.TABLE_NAME)
public class Empresa {
    
    public interface CreateEmpresa{}
    public interface UpdateEmpresa{}

    public static final String TABLE_NAME = "Empresa";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa", unique = true)
    private Long idEmpresa;

    @Column(name = "razao_social", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String razaoSocial;

    @Column(name = "tipo_empresa", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipoEmpresa;

    @Column(name = "cpf_cpnj", length = 14, nullable = false, unique = true)
    @NotBlank
    @Size(min = 14)
    private String cpfCnpj;

    @Column(name = "tipo_pessoa", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "email_empresa", length = 100, nullable = false, unique = true)
    @NotBlank
    @Size(min = 10, max = 60)
    @Email
    private String email;

    @Column(name = "telefone_empresa", length = 20, unique = true, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    private String telefone;

    @Column(name = "rua_empresa", length = 60, nullable = false)
    @NotBlank
    private String ruaEmpresa;

    @Column(name = "numero_empresa", length = 3, nullable = false)
    @NotNull
    @Min(1)
    private Integer numeroEmpresa;

    @Column(name = "bairro_empresa", length = 60, nullable = false)
    @NotBlank
    @Size(min = 3)
    private String bairroEmpresa;

    @Column(name = "cep_empresa", length = 8, nullable = false)
    @NotBlank
    @Size(min = 5)
    private String cepEmpresa;

    @Column(name = "estado_empresa", length = 60, nullable = false)
    @NotBlank
    private String estadoEmpresa;

    @Column(name = "pais_empresa", length = 50, nullable = false)
    @NotBlank
    private String paisEmpresa;

    // Constructors

    public Empresa(){}

    public Empresa(Long idEmpresa, String razaoSocial, TipoEmpresa tipoEmpresa, String cpfCnpj, TipoPessoa tipoPessoa,
                   String email, String telefone, String ruaEmpresa, Integer numeroEmpresa, String bairroEmpresa,
                   String cepEmpresa, String estadoEmpresa, String paisEmpresa) {
        this.idEmpresa = idEmpresa;
        this.razaoSocial = razaoSocial;
        this.tipoEmpresa = tipoEmpresa;
        this.cpfCnpj = cpfCnpj;
        this.tipoPessoa = tipoPessoa;
        this.email = email;
        this.telefone = telefone;
        this.ruaEmpresa = ruaEmpresa;
        this.numeroEmpresa = numeroEmpresa;
        this.bairroEmpresa = bairroEmpresa;
        this.cepEmpresa = cepEmpresa;
        this.estadoEmpresa = estadoEmpresa;
        this.paisEmpresa = paisEmpresa;
    }

    // Getters and Setters

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRuaEmpresa() {
        return ruaEmpresa;
    }

    public void setRuaEmpresa(String ruaEmpresa) {
        this.ruaEmpresa = ruaEmpresa;
    }

    public Integer getNumeroEmpresa() {
        return numeroEmpresa;
    }

    public void setNumeroEmpresa(Integer numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
    }

    public String getBairroEmpresa() {
        return bairroEmpresa;
    }

    public void setBairroEmpresa(String bairroEmpresa) {
        this.bairroEmpresa = bairroEmpresa;
    }

    public String getCepEmpresa() {
        return cepEmpresa;
    }

    public void setCepEmpresa(String cepEmpresa) {
        this.cepEmpresa = cepEmpresa;
    }

    public String getEstadoEmpresa() {
        return estadoEmpresa;
    }

    public void setEstadoEmpresa(String estadoEmpresa) {
        this.estadoEmpresa = estadoEmpresa;
    }

    public String getPaisEmpresa() {
        return paisEmpresa;
    }

    public void setPaisEmpresa(String paisEmpresa) {
        this.paisEmpresa = paisEmpresa;
    }
}

