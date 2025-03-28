package br.com.techblitz.storage.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
public abstract class ResponseDTO {
  private final OffsetDateTime timestamp;
  private final Integer status;
  
  public ResponseDTO(Integer status) {
    this.timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    this.status = status;
  }
}
