package pl.dawad.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.dawad.backend.exception.ExcelImportException;
import pl.dawad.backend.exception.IncompleteFormDataProvidedException;
import pl.dawad.backend.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(IncompleteFormDataProvidedException.class)
    public ResponseEntity<String> handleIncompleteFormDataProvidedException(IncompleteFormDataProvidedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> handleException(MailSendException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    @ExceptionHandler(ExcelImportException.class)
    public ResponseEntity<String> handleException(ExcelImportException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }

}
