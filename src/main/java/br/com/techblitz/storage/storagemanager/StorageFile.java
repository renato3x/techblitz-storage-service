package br.com.techblitz.storage.storagemanager;

import lombok.Getter;

@Getter
public class StorageFile extends StorageMetadata {
  private final byte[] bytes;
  
  public StorageFile(String filename, String contentType, Long size, String url, byte[] bytes) {
    super(filename, contentType, size, url);
    this.bytes = bytes;
  }
}
