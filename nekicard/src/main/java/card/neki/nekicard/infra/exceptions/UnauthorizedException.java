package card.neki.nekicard.infra.exceptions;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String ex) {
        super(ex);
    }
}
