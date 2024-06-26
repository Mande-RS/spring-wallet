package mandepaulo.wallet.exceptions;

public class BeneficiaryException extends Exception {
    private static final long serialVersionUID = 1L;

    public BeneficiaryException() {
        super();
    }

    public BeneficiaryException(String message) {
        super(message);
    }

    public BeneficiaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeneficiaryException(Throwable cause) {
        super(cause);
    }

}
