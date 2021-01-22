package dev.alvo.productinventory.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fixer")
public class FixerProperties {

  private String url;
  private String accessKey;

  public String getUrl() {
    return url;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }
}
