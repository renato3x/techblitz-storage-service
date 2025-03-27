package br.com.techblitz.storage.storagemanager.s3;

import br.com.techblitz.storage.storagemanager.StorageManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws-s3")
public class S3StorageManager implements StorageManager {
  private final S3StorageManagerConfig config;
  
  public S3StorageManager(S3StorageManagerConfig config) {
    this.config = config;
  }
  
  @Override
  public String upload(MultipartFile file) {
    return null;
  }
}
