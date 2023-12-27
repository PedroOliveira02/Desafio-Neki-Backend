package card.neki.nekicard.infra.exceptions.handler;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import card.neki.nekicard.infra.exceptions.EmailCadastradoException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  private ResponseEntity notFoundHandler() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity badRequestHandler(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();

    return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity handleDateTimeParseException(DateTimeParseException ex) {
    DadosErroValidacao erro = new DadosErroValidacao("dataNascimento",
        "Formato de data inválido. Use o formato dd-MM-yyyy.");
    return ResponseEntity.badRequest().body(List.of(erro));
  }

  @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity handleEmailCadastradoException(EmailCadastradoException ex) {
        DadosErroValidacao erro = new DadosErroValidacao("email", ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(erro));
    }

  private record DadosErroValidacao(String campo, String mensagem) {
    public DadosErroValidacao(FieldError erro) {
      this(erro.getField(), erro.getDefaultMessage());
    }
  }

}
