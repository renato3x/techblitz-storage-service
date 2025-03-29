package br.com.techblitz.storage.storagemanager.exception;

import lombok.Getter;

@Getter
public class StorageNotFoundException extends RuntimeException {
  private final String filename;
  
  public StorageNotFoundException(String filename) {
    super();
    this.filename = filename;
  }
}
