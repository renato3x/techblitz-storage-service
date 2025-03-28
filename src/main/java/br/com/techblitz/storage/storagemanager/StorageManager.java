package br.com.techblitz.storage.storagemanager;

import org.springframework.web.multipart.MultipartFile;

public interface StorageManager {
  StorageMetadata upload(MultipartFile file, String path, String filename);
}
