package pl.dawad.backend.exception;

public class IncompleteFormDataProvidedException extends RuntimeException{
    public IncompleteFormDataProvidedException(String message) {
        super(message);
    }
}
