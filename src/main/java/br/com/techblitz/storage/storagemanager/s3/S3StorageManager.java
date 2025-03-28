package br.com.techblitz.storage.storagemanager.s3;

import br.com.techblitz.storage.storagemanager.Download;
import br.com.techblitz.storage.storagemanager.StorageManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

@Component
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws-s3")
public class S3StorageManager implements StorageManager {
  private final S3StorageManagerConfig config;
  private final S3Client s3Client;
  
  public S3StorageManager(S3StorageManagerConfig config) {
    this.config = config;
    var credentials = AwsBasicCredentials.create(
      this.config.getAccessKeyId(),
      this.config.getSecretAccessKey()
    );

    var builder = S3Client.builder();
    builder.region(Region.of(this.config.getRegion()));
    builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
    
    if (this.config.getEndpoint() != null && !this.config.getEndpoint().isBlank()) {
      builder.endpointOverride(URI.create(this.config.getEndpoint()));
      builder.serviceConfiguration(
        S3Configuration.builder()
          .pathStyleAccessEnabled(true)
          .build()
      );
    }
    
    this.s3Client = builder.build();
  }
  
  @Override
  public void upload(MultipartFile file, String path) {
    try {
      var putObjectRequest = PutObjectRequest.builder()
        .bucket(this.config.getBucketName())
        .key(path)
        .contentType(file.getContentType())
        .build();
      
      this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException("Error storing file");
    }
  }
  @Override
  public Download download(String path) {
    var getObjectRequest = GetObjectRequest.builder()
      .bucket(this.config.getBucketName())
      .key(path)
      .build();
    
    var response = this.s3Client.getObject(getObjectRequest);
    
    try {
      var contentType = response.response().contentType();
      var bytes = response.readAllBytes();
      
      return new Download(bytes, path, contentType);
    } catch (Exception e) {
      throw new RuntimeException("Error downloading file");
    }
  }
}
