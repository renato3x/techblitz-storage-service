package br.com.techblitz.storage.service;

import br.com.techblitz.storage.config.ApplicationConfig;
import br.com.techblitz.storage.dto.FileUploadResponseDTO;
import br.com.techblitz.storage.storagemanager.StorageManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class AvatarStorageService {
  private final StorageManager storageManager;
  private final ApplicationConfig applicationConfig;
  
  public AvatarStorageService(StorageManager storageManager, ApplicationConfig applicationConfig) {
    this.storageManager = storageManager;
    this.applicationConfig = applicationConfig;
  }
  
  public FileUploadResponseDTO upload(MultipartFile file, String username) {
    var filename = UUID.randomUUID().toString();
    var extension = FilenameUtils.getExtension(file.getOriginalFilename());
    var fullFilename = filename + "." + extension;
    var path = "/avatars/" + username + "/" + fullFilename;
    var url = this.applicationConfig.getUrl() + path;

    this.storageManager.upload(file, path);
    return new FileUploadResponseDTO(fullFilename, file.getContentType(), url, file.getSize());
  }
}
