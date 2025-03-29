package br.com.techblitz.storage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private final String message;
  private final Integer status;
  private final OffsetDateTime timestamp;
  private final Object details;
  
  public ErrorResponse(Integer status, String message, Object details) {
    this.message = message;
    this.status = status;
    this.timestamp = OffsetDateTime.now();
    this.details = details;
  }

  public ErrorResponse(Integer status, String message) {
    this.message = message;
    this.status = status;
    this.timestamp = OffsetDateTime.now();
    this.details = null;
  }
}
