package br.com.techblitz.storage.storagemanager.s3;

import br.com.techblitz.storage.config.ApplicationConfig;
import br.com.techblitz.storage.storagemanager.StorageFile;
import br.com.techblitz.storage.storagemanager.StorageManager;
import br.com.techblitz.storage.storagemanager.StorageMetadata;
import br.com.techblitz.storage.storagemanager.exception.StorageNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.net.URI;

@Component
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws-s3")
public class S3StorageManager implements StorageManager {
  private final ApplicationConfig applicationConfig;
  private final S3StorageManagerConfig config;
  private S3Client s3Client;
  
  public S3StorageManager(S3StorageManagerConfig config, ApplicationConfig applicationConfig) {
    this.config = config;
    this.applicationConfig = applicationConfig;
    this.createS3Client();
  }
  
  private void createS3Client() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(
      this.config.getAccessKeyId(),
      this.config.getSecretAccessKey()
    );

    S3ClientBuilder builder = S3Client.builder();
    builder.region(Region.of(this.config.getRegion()));
    builder.credentialsProvider(StaticCredentialsProvider.create(credentials));

    // configuration to use LocalStack
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
  public StorageMetadata upload(MultipartFile file, String path, String filename) {
    final String finalPath = path + "/" + filename;
    final String url = this.applicationConfig.getUrl() + "/" + finalPath;
    PutObjectRequest request = PutObjectRequest.builder()
      .bucket(this.config.getBucketName())
      .key(finalPath)
      .contentType(file.getContentType())
      .build();
    
    try {
      InputStream fileInputStream = file.getInputStream();
      this.s3Client.putObject(request, RequestBody.fromBytes(fileInputStream.readAllBytes()));
      return new StorageMetadata(filename, file.getContentType(), file.getSize(), url);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public StorageFile get(String path, String filename) {
    final String finalPath = path + "/" + filename;
    final String url = this.applicationConfig.getUrl() + "/" + finalPath;
    GetObjectRequest request = GetObjectRequest.builder().bucket(this.config.getBucketName()).key(finalPath).build();
    
    try {
      ResponseInputStream<GetObjectResponse> response = this.s3Client.getObject(request);
      String contentType = response.response().contentType();
      Long size = response.response().contentLength();
      return new StorageFile(filename, contentType, size, url, response.readAllBytes());
    } catch (NoSuchKeyException e) {
      throw new StorageNotFoundException(filename);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
