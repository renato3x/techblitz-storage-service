package br.com.techblitz.storage.service.storage;

import br.com.techblitz.storage.config.ApplicationConfig;
import br.com.techblitz.storage.exception.RequestException;
import br.com.techblitz.storage.storagemanager.StorageFile;
import br.com.techblitz.storage.storagemanager.StorageManager;
import br.com.techblitz.storage.storagemanager.StorageMetadata;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
public class AvatarStorageService {
  private final String basePath = "avatars";
  private final StorageManager storageManager;
  private final ApplicationConfig applicationConfig;
  
  public AvatarStorageService(StorageManager storageManager, ApplicationConfig applicationConfig) {
    this.storageManager = storageManager;
    this.applicationConfig = applicationConfig;
  }
  
  public StorageMetadata upload(MultipartFile file) {
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    
    if (!this.applicationConfig.isAllowedImageExtension(extension)) {
      throw new RequestException(
        HttpStatus.BAD_REQUEST,
        "The image extension is not allowed",
        Map.of(
          "extension", extension == null ? "" : extension,
          "allowedExtensions", this.applicationConfig.getAllowedImageExtensions()
        )
      );
    }
    
    String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    return this.storageManager.upload(file, basePath, filename);
  }
  
  public StorageFile get(String filename) {
    return this.storageManager.get(basePath, filename);
  }
}
