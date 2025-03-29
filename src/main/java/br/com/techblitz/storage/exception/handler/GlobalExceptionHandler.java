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
      exception.getStatus().value(),
      exception.getMessage(),
      exception.getDetails()
    );
    
    return new ResponseEntity<>(response, exception.getStatus());
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Unexpected error occurred"
    );
    
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
