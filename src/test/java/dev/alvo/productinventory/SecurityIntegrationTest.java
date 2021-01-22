package dev.alvo.productinventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecurityIntegrationTest {

  @LocalServerPort
  private int port;

  private TestRestTemplate restTemplate;
  private URL base;

  @BeforeEach
  public void setUp() throws MalformedURLException {
    base = new URL("http://localhost:" + port);
  }

  @Test
  public void whenLoggedUserRequestsProductsThenSuccess() throws IllegalStateException {
    var restTemplate = new TestRestTemplate("super", "super");
    var response =
      restTemplate.getForEntity(base.toString() + "/api/v1/private/product/all", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void userWithProductsAccessPermissionThenSuccess() throws IllegalStateException {
    var restTemplate = new TestRestTemplate("product_manager", "product");
    var response =
      restTemplate.getForEntity(base.toString() + "/api/v1/private/product/all", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void userWithoutProductsAccessPermissionThenForbidden() throws IllegalStateException {
    var restTemplate = new TestRestTemplate("category_manager", "category");
    var response =
      restTemplate.getForEntity(base.toString() + "/api/v1/private/product/all", String.class);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void whenUserWithWrongCredentialsThenUnauthorized() {
    var restTemplate = new TestRestTemplate("wrong_user", "wrong_password");
    var response = restTemplate.getForEntity(base.toString(), String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}
