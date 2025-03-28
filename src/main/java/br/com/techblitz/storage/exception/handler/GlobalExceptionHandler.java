package br.com.techblitz.storage.exception.handler;

import br.com.techblitz.storage.dto.ErrorResponse;
import br.com.techblitz.storage.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RequestException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(RequestException exception) {
    ErrorResponse response = new ErrorResponse(
      exception.getMessage(),
      exception.getStatus().value(),
      exception.getDetails()
    );
    
    return new ResponseEntity<>(response, exception.getStatus());
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = new ErrorResponse(
      "Unexpected error occurred",
      HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
