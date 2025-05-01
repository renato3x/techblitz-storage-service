package br.com.techblitz.storage.config;

import br.com.techblitz.storage.security.JwtCookieCleanerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final BearerTokenResolver bearerTokenResolver;
  private final JwtCookieCleanerFilter jwtCookieCleanerFilter;
  
  public SecurityConfig(BearerTokenResolver bearerTokenResolver, JwtCookieCleanerFilter jwtCookieCleanerFilter) {
    this.bearerTokenResolver = bearerTokenResolver;
    this.jwtCookieCleanerFilter = jwtCookieCleanerFilter;
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/v1/avatars/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/v1/avatars").hasAuthority("SCOPE_avatars:upload")
        .requestMatchers(HttpMethod.DELETE, "/v1/avatars/**").hasAuthority("SCOPE_avatars:delete")
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .bearerTokenResolver(this.bearerTokenResolver)
        .jwt(Customizer.withDefaults())
      )
      .addFilterAfter(this.jwtCookieCleanerFilter, BearerTokenAuthenticationFilter.class);
    
    return http.build();
  }
}
