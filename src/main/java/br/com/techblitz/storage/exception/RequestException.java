package br.com.techblitz.storage.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class RequestException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;
  private final String message;
  private final HttpStatus status;
  private final Object details;
  
  public RequestException(String message, HttpStatus status, Object details) {
    this.message = message;
    this.status = status;
    this.details = details;
  }
  
  public RequestException(String message, HttpStatus status) {
    this(message, status, null);
  }
}
