package br.com.techblitz.storage.storagemanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StorageMetadata {
  private final String filename;
  @JsonProperty("content_type")
  private final String contentType;
  private final Long size;
  private final String url;
}
