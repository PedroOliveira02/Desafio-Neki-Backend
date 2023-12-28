package card.neki.nekicard.infra.exceptions;

public class LoginBadRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LoginBadRequestException(String ex) {
    super(ex);
  }
}
