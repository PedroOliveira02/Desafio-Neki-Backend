package card.neki.nekicard.infra.exceptions;

public class TokenGenerationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenGenerationException(String ex) {
        super(ex);
    }
}