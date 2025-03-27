package br.com.techblitz.storage.storagemanager.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "storage.aws-s3")
@ConditionalOnProperty(name = "storage.provider", havingValue = "aws-s3")
public class S3StorageManagerConfig {
  private String region;
  private String accessKeyId;
  private String secretAccessKey;
  private String endpoint;
  private String bucketName;
}
