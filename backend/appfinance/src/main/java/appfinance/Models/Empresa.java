package appfinance.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Empresa.TABLE_NAME)
public class Empresa {
    
    public interface CreateEmpresa{}
    public interface UpdateEmpresa{}

    public static final String TABLE_NAME = "Empresa";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa", unique = true)
    private Long idEmpresa;

    @Column(name = "razaoSocial", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String razaoSocial;

    @Column(name = "nomeFantasia", length = 100, nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String nomeFantasia;

    /*
     * 0 - Cliente
     * 1 - Fornecedor
     * 2 - Ambos
     */
    @Column(name = "tipoEmpresa", nullable = false)
    @NotNull
    private int tipoEmpresa;

    @Column(name = "cpf_cpnj", length = 20, nullable = false, unique = true)
    @NotBlank
    @Size(min = 11)
    private String cpfCnpj;

    /*
     * 0 - Física
     * 1 - Jurídica
     */
    @Column(name = "tipoPessoa", nullable = false)
    @NotNull
    private int tipoPessoa;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank
    @Size(min = 10, max = 60)
    @Email
    private String email;

    @Column(name = "telefone", length = 20, unique = true, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20)
    private String telefone;

    @Column(name = "rua", length = 60, nullable = false)
    @NotBlank
    private String ruaEmpresa;

    @Column(name = "numero", length = 3, nullable = false)
    @NotNull
    @Min(1)
    private Integer numeroEmpresa;

    @Column(name = "bairro", length = 60, nullable = false)
    @NotBlank
    @Size(min = 3)
    private String bairroEmpresa;

    @Column(name = "cep", length = 9, nullable = false)
    @NotBlank
    @Size(min = 5)
    private String cepEmpresa;

    @Column(name = "cidade", length = 50, nullable = false)
    @NotBlank
    @Size(min = 3)
    private String cidadeEmpresa;

    @Column(name = "estado", length = 60, nullable = false)
    @NotBlank
    private String estadoEmpresa;

    @Column(name = "pais", length = 50, nullable = false)
    @NotBlank
    private String paisEmpresa;

    public Empresa orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}

