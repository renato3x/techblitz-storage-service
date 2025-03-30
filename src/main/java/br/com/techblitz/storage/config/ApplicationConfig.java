package br.com.techblitz.storage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
  private String url;
  private List<String> allowedImageExtensions;
  
  public boolean isAllowedImageExtension(String extension) {
    return allowedImageExtensions.contains(extension);
  }
}
