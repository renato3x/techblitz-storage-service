package br.com.techblitz.storage.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {

  @Override
  public String resolve(HttpServletRequest request) {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("storage_auth_token")) {
          return cookie.getValue();
        }
      }
    }

    return null;
  }
}
