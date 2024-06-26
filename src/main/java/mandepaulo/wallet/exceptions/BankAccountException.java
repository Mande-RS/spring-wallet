package mandepaulo.wallet.exceptions;

public class BankAccountException extends Exception {

    private static final long serialVersionUID = 1L;

    public BankAccountException() {
        super();
    }

    public BankAccountException(String message) {
        super(message);
    }

    public BankAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankAccountException(Throwable cause) {
        super(cause);
    }
}
