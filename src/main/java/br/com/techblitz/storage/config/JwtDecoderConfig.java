package br.com.techblitz.storage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {
  @Value("${security.jwt.signin-key}")
  private String signingKey;
  
  @Bean
  public JwtDecoder jwtDecoder() {
    byte[] keyBytes = this.signingKey.getBytes(StandardCharsets.UTF_8);
    SecretKey secretKey = new SecretKeySpec(keyBytes, "HMACSHA256");
    return NimbusJwtDecoder.withSecretKey(secretKey).build();
  }
}
