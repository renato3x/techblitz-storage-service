package br.com.techblitz.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
        .jwt(Customizer.withDefaults())
      );
    
    return http.build();
  }
}
