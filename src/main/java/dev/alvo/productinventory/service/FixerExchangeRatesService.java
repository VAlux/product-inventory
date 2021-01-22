package dev.alvo.productinventory.service;

import dev.alvo.productinventory.config.properties.FixerProperties;
import dev.alvo.productinventory.model.FixerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class FixerExchangeRatesService {

  public static final String EUR_CURRENCY_CODE = "EUR";
  private final FixerProperties fixerProperties;
  private final RestTemplate restTemplate;

  @Autowired
  public FixerExchangeRatesService(FixerProperties fixerProperties, RestTemplate restTemplate) {
    this.fixerProperties = fixerProperties;
    this.restTemplate = restTemplate;
  }

  public Optional<BigDecimal> exchangeRate(Currency base, Currency target) {
    var entity = new HttpEntity<>(createHeaders());
    var uriBuilder = UriComponentsBuilder.fromUriString(fixerProperties.getUrl())
      .path("latest")
      .queryParam("access_key", fixerProperties.getAccessKey())
      .queryParam("base", base.getCurrencyCode())
      .queryParam("symbols", target.getCurrencyCode());

    var response = restTemplate
      .exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, FixerResponse.class)
      .getBody();

    if (response != null) {
      return Optional.ofNullable(response.getRates().get(target.getCurrencyCode()));
    } else {
      return Optional.empty();
    }
  }

  private HttpHeaders createHeaders() {
    var headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    return headers;
  }
}
