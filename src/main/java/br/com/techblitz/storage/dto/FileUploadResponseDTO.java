package br.com.techblitz.storage.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileUploadResponseDTO extends ResponseDTO {
  private final String filename;
  private final String contentType;
  private final String url;
  private final Long size;
  
  public FileUploadResponseDTO(String filename, String contentType, String url, Long size) {
    super(HttpStatus.CREATED.value());
    this.filename = filename;
    this.contentType = contentType;
    this.url = url;
    this.size = size;
  }
}
