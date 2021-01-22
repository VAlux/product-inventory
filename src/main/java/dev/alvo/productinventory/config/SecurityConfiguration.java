package dev.alvo.productinventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final Set<String> whiteList = Set.of(
    "**/public/**",
    "/swagger-ui/**",
    "/swagger-resources/**",
    "/webjars/**",
    "/v2/api-docs"
  );

  public static final String PRODUCT_MANAGER_ROLE = "PRODUCT_MANAGER";
  public static final String CATEGORY_MANAGER_ROLE = "CATEGORY_MANAGER";
  public static final String SUPER_USER_ROLE = "SUPER_USER";
  public static final String PRODUCT_ACCESS_PERMISSION = "PRODUCT_ACCESS";
  public static final String CATEGORY_ACCESS_PERMISSION = "CATEGORY_ACCESS";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .anonymous().and()
      .authorizeRequests()
      .antMatchers(whiteList.toArray(String[]::new)).permitAll()
      .anyRequest().authenticated().and()
      .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    final var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    // just for the sake of the demo...
    auth
      .inMemoryAuthentication()
      .withUser("super")
      .password(encoder.encode("super"))
      .roles(SUPER_USER_ROLE)
      .authorities(PRODUCT_ACCESS_PERMISSION, CATEGORY_ACCESS_PERMISSION)
      .and()
      .withUser("product_manager")
      .password(encoder.encode("product"))
      .roles(PRODUCT_MANAGER_ROLE)
      .authorities(PRODUCT_ACCESS_PERMISSION)
      .and()
      .withUser("category_manager")
      .password(encoder.encode("category"))
      .roles(CATEGORY_MANAGER_ROLE)
      .authorities(CATEGORY_ACCESS_PERMISSION);
  }
}
