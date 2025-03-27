package br.com.techblitz.storage.storagemanager;

import org.springframework.web.multipart.MultipartFile;

public interface StorageManager {
  public String upload(MultipartFile file);
}
