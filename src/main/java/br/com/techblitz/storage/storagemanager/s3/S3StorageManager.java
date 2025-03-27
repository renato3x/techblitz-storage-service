package br.com.techblitz.storage.storagemanager.s3;

import br.com.techblitz.storage.storagemanager.StorageManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Component
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws-s3")
public class S3StorageManager implements StorageManager {
  private final S3Client s3Client;
  
  public S3StorageManager(S3StorageManagerConfig config) {
    var credentials = AwsBasicCredentials.create(
      config.getAccessKeyId(),
      config.getSecretAccessKey()
    );

    var builder = S3Client.builder();
    builder.region(Region.of(config.getRegion()));
    builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
    
    if (config.getEndpoint() != null && !config.getEndpoint().isBlank()) {
      builder.endpointOverride(URI.create(config.getEndpoint()));
    }
    
    this.s3Client = builder.build();
  }
  
  @Override
  public String upload(MultipartFile file) {
    return null;
  }
}
