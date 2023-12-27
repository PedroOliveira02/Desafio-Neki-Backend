package card.neki.nekicard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdministradorDto(
  Long id, 

  @NotBlank
  @Size(max = 80)
  String nome, 

  @NotBlank
  @Email
  @Pattern(regexp = ".+@(neki-it\\.com\\.br|neki\\.com\\.br)$", message = "O e-mail deve terminar com @neki-it.com.br ou @neki.com.br")
  String email, 

  @NotBlank
  @Size(max = 80)
  String senha
  ) {}
