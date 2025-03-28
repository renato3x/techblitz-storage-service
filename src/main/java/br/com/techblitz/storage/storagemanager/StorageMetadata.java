package br.com.techblitz.storage.storagemanager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StorageMetadata {
  private final String filename;
  private final String contentType;
  private final Long size;
  private final String url;
}
