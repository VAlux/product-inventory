package dev.alvo.productinventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static dev.alvo.productinventory.service.FixerExchangeRatesService.EUR_CURRENCY_CODE;
import static java.math.RoundingMode.HALF_EVEN;

@Service
public class ProductPriceCalculationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductPriceCalculationService.class);

  private final FixerExchangeRatesService fixerExchangeRatesService;
  private final Currency baseCurrency;

  @Autowired
  public ProductPriceCalculationService(FixerExchangeRatesService fixerExchangeRatesService) {
    this.fixerExchangeRatesService = fixerExchangeRatesService;
    this.baseCurrency = Currency.getInstance(EUR_CURRENCY_CODE);
  }

  public Optional<BigDecimal> calculatePrice(String currency, BigDecimal price) {
    if (currency == null || EUR_CURRENCY_CODE.equals(currency)) {
      return Optional.ofNullable(price);
    }

    try {
      return fixerExchangeRatesService
        .exchangeRate(baseCurrency, Currency.getInstance(currency))
        .map(rate -> convertPrice(price, rate));
    } catch (Exception ex) {
      LOGGER.error("Unsupported currency code: {}", currency, ex);
      return Optional.empty();
    }
  }

  private BigDecimal convertPrice(BigDecimal price, BigDecimal rate) {
    return price
      .multiply(BigDecimal.ONE.divide(rate, 4, HALF_EVEN))
      .setScale(2, HALF_EVEN);
  }
}
