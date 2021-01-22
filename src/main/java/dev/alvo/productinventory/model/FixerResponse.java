package dev.alvo.productinventory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerResponse {

  private final String base;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate date;

  private final Map<String, BigDecimal> rates;

  @JsonCreator
  public FixerResponse(@JsonProperty("base") String base,
                       @JsonProperty("date") LocalDate date,
                       @JsonProperty("rates") Map<String, BigDecimal> rates) {

    this.base = base;
    this.date = date;
    this.rates = rates;
  }

  public String getBase() {
    return base;
  }

  public LocalDate getDate() {
    return date;
  }

  public Map<String, BigDecimal> getRates() {
    return rates;
  }
}
