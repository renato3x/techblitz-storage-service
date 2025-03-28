package br.com.techblitz.storage.service.storage;

import br.com.techblitz.storage.storagemanager.StorageFile;
import br.com.techblitz.storage.storagemanager.StorageManager;
import br.com.techblitz.storage.storagemanager.StorageMetadata;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class AvatarStorageService {
  private final String basePath = "avatars";
  private final StorageManager storageManager;
  
  public AvatarStorageService(StorageManager storageManager) {
    this.storageManager = storageManager;
  }
  
  public StorageMetadata upload(MultipartFile file) {
    String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    return this.storageManager.upload(file, basePath, filename);
  }
  
  public StorageFile get(String filename) {
    return this.storageManager.get(basePath, filename);
  }
}
