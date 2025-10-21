package appfinance.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = Usuario.TABLE_NAME)
public class Usuario {

    public interface CreateUsuario{}
    public interface UpdateUsuario{}

    public static final String TABLE_NAME = "Usuario";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nome_usuario", nullable = false, length = 50)
    @NotBlank
    private String nomeUsuario;

    @Column(name = "email_usuario", nullable = false, length = 100, unique = true)
    @NotBlank
    @Size(min = 5, max = 100)
    private String emailUsuario;

    @Column(name = "senha_usuario", nullable = false)
    @NotBlank
    @Size(min = 8, max = 100)
    private String senhaHashUsuario;
}
