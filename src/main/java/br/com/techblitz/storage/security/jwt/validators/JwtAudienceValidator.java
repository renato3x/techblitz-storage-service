package br.com.techblitz.storage.security.jwt.validators;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

@AllArgsConstructor
public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {
  private String audience;
  
  @Override
  public OAuth2TokenValidatorResult validate(Jwt token) {
    if (token.getAudience().size() == 1 && token.getAudience().getFirst().equals(this.audience)) {
      return OAuth2TokenValidatorResult.success();
    }

    OAuth2Error error = new OAuth2Error("invalid_token");
    return OAuth2TokenValidatorResult.failure(error);
  }
}
