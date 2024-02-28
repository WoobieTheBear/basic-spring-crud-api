package io.black.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PublicResponse> handleNotFoundException(NotFoundException exception){
        PublicResponse response = new PublicResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PublicResponse> handleAnyException(Exception exception){
        String message = exception.getMessage();
        if (message.contains("Failed to convert")) {
            message = "Please make sure to use the correct types in your parameters.";
        }
        if (message.contains("ERROR: duplicate key") && message.contains("email")) {
            message = "There is already a user registered under this email.";
        }
        PublicResponse response = new PublicResponse(
            HttpStatus.BAD_REQUEST.value(),
            message,
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
