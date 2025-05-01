package br.com.techblitz.storage.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCookieCleanerFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("storage_auth_token")) {
          Cookie clearCookie = new Cookie(cookie.getName(), null);
          clearCookie.setMaxAge(0);
          clearCookie.setPath("/");
          clearCookie.setHttpOnly(true);
          response.addCookie(clearCookie);
          break;
        }
      }
    }
    
    filterChain.doFilter(request, response);
  }
}
