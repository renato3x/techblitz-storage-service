package br.com.techblitz.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponseDTO {
  private String filename;
  private String contentType;
  private String url;
  private Long size;
}
