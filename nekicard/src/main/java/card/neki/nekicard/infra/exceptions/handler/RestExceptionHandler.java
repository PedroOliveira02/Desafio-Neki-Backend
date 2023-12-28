package card.neki.nekicard.infra.exceptions.handler;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import card.neki.nekicard.infra.exceptions.EmailCadastradoException;
import card.neki.nekicard.infra.exceptions.LoginBadRequestException;
import card.neki.nekicard.infra.exceptions.TokenGenerationException;
import card.neki.nekicard.infra.exceptions.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  private ResponseEntity<?> notFoundHandler() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> badRequestHandler(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();

    return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("dataNascimento",
        "Formato de data inválido. Use o formato dd-MM-yyyy.");
    return ResponseEntity.badRequest().body(List.of(erro));
  }

  @ExceptionHandler(EmailCadastradoException.class)
  public ResponseEntity<?> handleEmailCadastradoException(EmailCadastradoException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("email", ex.getMessage());
    return ResponseEntity.badRequest().body(List.of(erro));
  }

  @ExceptionHandler(LoginBadRequestException.class)
  public ResponseEntity<?> handleLoginBadRequestoException(LoginBadRequestException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("login", ex.getMessage());
    return ResponseEntity.badRequest().body(List.of(erro));
  }

  @ExceptionHandler(TokenGenerationException.class)
  public ResponseEntity<?> handleTokenGenerationException(TokenGenerationException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("token", ex.getMessage());
    return ResponseEntity.badRequest().body(List.of(erro));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("token", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(List.of(erro));
  }

  private record DadosErroValidacao(String campo, String mensagem) {
    public DadosErroValidacao(FieldError erro) {
      this(erro.getField(), erro.getDefaultMessage());
    }
  }

}
