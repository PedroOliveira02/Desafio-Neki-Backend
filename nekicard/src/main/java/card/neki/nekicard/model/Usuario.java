package card.neki.nekicard.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, name = "nome_completo", length = 80)
  private String nomeCompleto;

  @Column(name = "nome_social", length = 80)
  private String nomeSocial;

  @NotNull
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Column(nullable = false, name = "data_nascimento")
  private LocalDate dataNascimento;

  @NotEmpty
  @Column(nullable = false)
  private byte[] foto;

  @NotBlank
  @Email
  @Pattern(regexp = ".+@(neki-it\\.com\\.br|neki\\.com\\.br)$", message = "O e-mail deve terminar com @neki-it.com.br ou @neki.com.br")
  @Column(nullable = false)
  private String email;

  @Column(length = 15)
  private String telefone;

  @Column(name = "linked_in")
  private String linkedIn;
  private String github;
  private String facebook;
  private String instagram;

}
