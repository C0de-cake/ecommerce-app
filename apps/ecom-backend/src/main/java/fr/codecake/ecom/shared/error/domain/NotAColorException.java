package fr.codecake.ecom.shared.error.domain;

public class NotAColorException extends AssertionException {

    public NotAColorException(String field, String message) {
        super(field, message);
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NOT_A_COLOR;
    }
}
