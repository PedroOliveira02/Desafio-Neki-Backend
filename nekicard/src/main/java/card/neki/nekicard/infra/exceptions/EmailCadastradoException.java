package card.neki.nekicard.infra.exceptions;

public class EmailCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailCadastradoException(String ex) {
        super(ex);
    }
}
