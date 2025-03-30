package br.com.techblitz.storage.config;

import br.com.techblitz.storage.security.jwt.validators.JwtAudienceValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {
  @Value("${security.jwt.signin-key}")
  private String signingKey;
  
  @Value("${security.jwt.issuer}")
  private String issuer;
  
  @Value("${security.jwt.audience}")
  private String audience;
  
  @Bean
  public JwtDecoder jwtDecoder() {
    byte[] keyBytes = this.signingKey.getBytes(StandardCharsets.UTF_8);
    SecretKey secretKey = new SecretKeySpec(keyBytes, "HMACSHA256");
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();

    OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(this.issuer);
    OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(this.audience);
    
    OAuth2TokenValidator<Jwt> withIssuerAndAudience = 
      new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator);
    
    jwtDecoder.setJwtValidator(withIssuerAndAudience);
    
    return jwtDecoder;
  }
}
