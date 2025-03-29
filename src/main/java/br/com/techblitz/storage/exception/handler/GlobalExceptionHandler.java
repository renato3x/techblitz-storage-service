package br.com.techblitz.storage.exception.handler;

import br.com.techblitz.storage.dto.ErrorResponse;
import br.com.techblitz.storage.exception.RequestException;
import br.com.techblitz.storage.storagemanager.exception.StorageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

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
  
  @ExceptionHandler(StorageNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleStorageNotFoundException(StorageNotFoundException exception) {
    ErrorResponse response = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      "File " + exception.getFilename() + " not found",
      Map.of(
        "filename", exception.getFilename()
      )
    );
    
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException() {
    ErrorResponse response = new ErrorResponse(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Unexpected error occurred"
    );
    
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
