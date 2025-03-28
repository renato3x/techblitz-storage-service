package br.com.techblitz.storage.service;

import br.com.techblitz.storage.config.ApplicationConfig;
import br.com.techblitz.storage.dto.response.FileUploadResponse;
import br.com.techblitz.storage.storagemanager.Download;
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
  
  public FileUploadResponse upload(MultipartFile file, String username) {
    var filename = UUID.randomUUID().toString();
    var extension = FilenameUtils.getExtension(file.getOriginalFilename());
    var fullFilename = filename + "." + extension;
    var path = "/avatars/" + username + "/" + fullFilename;
    var url = this.applicationConfig.getUrl() + path;
    
    this.storageManager.upload(file, path);
    return new FileUploadResponse(fullFilename, file.getContentType(), url, file.getSize());
  }
  
  public Download download(String username, String filename) {
    var path = "/avatars/" + username + "/" + filename;
    return this.storageManager.download(path);
  }
}
