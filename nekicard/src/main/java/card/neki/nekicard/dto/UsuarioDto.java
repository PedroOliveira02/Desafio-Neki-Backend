package card.neki.nekicard.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDto(
  Long id,
  
  @NotBlank
  @Size(max = 80)
  String nomeCompleto,

  @Size(max = 80)
  String nomeSocial,

  @NotNull
  @JsonFormat(pattern = "dd-MM-yyyy") 
  LocalDate dataNascimento,

  @NotEmpty
  @Column(nullable = false)
  byte[] foto,

  @NotBlank
  @Email
  @Pattern(regexp = ".+@(neki-it\\.com\\.br|neki\\.com\\.br)$", message = "O e-mail deve terminar com @neki-it.com.br ou @neki.com.br")
  String email,

  @Size(max = 15)
  String telefone,

  String linkedIn,
  String github,
  String facebook,
  String instagram
) {}
