package dev.alvo.productinventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("Product inventory Core API 1.0.0")
      .apiInfo(apiInfo("1.0.0"))
      .select()
      .paths(PathSelectors.ant("/api/**"))
      .build();
  }


  private ApiInfo apiInfo(String version) {
    return new ApiInfoBuilder()
      .title("Product inventory core API v" + version)
      .description("Product inventory core service API")
      .version(version)
      .build();
  }
}
