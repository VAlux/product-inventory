package dev.alvo.productinventory.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;

@Configuration
public class ObjectMapperConfiguration implements Jackson2ObjectMapperBuilderCustomizer {

  private static final String DATE_FORMAT = "yyyy-MM-dd";

  @Override
  public void customize(Jackson2ObjectMapperBuilder builder) {
    builder.simpleDateFormat(DATE_FORMAT);
    builder.modules(new JavaTimeModule());
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
  }
}
