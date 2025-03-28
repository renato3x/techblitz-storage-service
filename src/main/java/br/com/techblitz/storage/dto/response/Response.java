package br.com.techblitz.storage.dto.response;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
public class Response<T> {
  private final OffsetDateTime timestamp;
  private final Integer status;
  private final T data;
  
  public Response(Integer status, T data) {
    this.timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    this.status = status;
    this.data = data;
  }
}
