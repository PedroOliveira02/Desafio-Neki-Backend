package card.neki.nekicard.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDto(
    Long id,

    @Size(max = 80) String nomeCompleto,

    @Size(max = 80) String nomeSocial,

    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,

    byte[] foto,

    @Email @Pattern(regexp = ".+@(neki-it\\.com\\.br|neki\\.com\\.br)$", message = "O e-mail deve terminar com @neki-it.com.br ou @neki.com.br") String email,

    @Size(min = 11, max = 18) String telefone,

    String linkedIn,
    String github,
    String facebook,
    String instagram) {
}
