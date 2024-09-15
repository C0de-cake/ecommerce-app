package fr.codecake.ecom.shared.authentication.infrastructure.primary;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers(HttpMethod.GET, "api/categories").permitAll()
          .requestMatchers(HttpMethod.GET, "api/products-shop/**").permitAll()
          .requestMatchers(HttpMethod.GET, "api/orders/get-cart-details").permitAll()
          .requestMatchers(HttpMethod.POST, "api/orders/webhook").permitAll()
          .requestMatchers("/api/**").authenticated())
      .csrf(AbstractHttpConfigurer::disable)
      .oauth2ResourceServer(
        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new KindeJwtAuthenticationConverter())));

    return http.build();
  }

}
