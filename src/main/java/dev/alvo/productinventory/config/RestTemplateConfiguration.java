package dev.alvo.productinventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    var httpMessageConverter = new MappingJackson2HttpMessageConverter();
    httpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));

    var stringMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);

    var template = new RestTemplate();
    template.getMessageConverters().add(0, stringMessageConverter);
    template.getMessageConverters().add(httpMessageConverter);

    return template;
  }
}
