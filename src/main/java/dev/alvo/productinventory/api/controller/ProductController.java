package dev.alvo.productinventory.api.controller;

import dev.alvo.productinventory.api.reponse.product.ErrorCreatingProductResponse;
import dev.alvo.productinventory.api.reponse.product.PriceCalculationErrorResponse;
import dev.alvo.productinventory.api.reponse.product.ProductNotFoundResponse;
import dev.alvo.productinventory.api.reponse.product.ProductResponse;
import dev.alvo.productinventory.api.reponse.product.ProductRetrievedResponse;
import dev.alvo.productinventory.api.request.product.ProductCreateRequest;
import dev.alvo.productinventory.api.request.product.ProductUpdateRequest;
import dev.alvo.productinventory.model.Product;
import dev.alvo.productinventory.service.ProductPriceCalculationService;
import dev.alvo.productinventory.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.alvo.productinventory.config.SecurityConfiguration.PRODUCT_ACCESS_PERMISSION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/private/product")
public class ProductController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

  private final ProductService productService;
  private final ProductPriceCalculationService priceCalculationService;

  @Autowired
  public ProductController(ProductService productService, ProductPriceCalculationService priceCalculationService) {
    this.productService = productService;
    this.priceCalculationService = priceCalculationService;
  }

  @ApiOperation(value = "Create new product", httpMethod = "POST")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully created new product"),
    @ApiResponse(code = 400, message = "Error creating new product"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + PRODUCT_ACCESS_PERMISSION + "')")
  public ResponseEntity<ProductResponse> createProduct(@Validated @RequestBody ProductCreateRequest request) {
    LOGGER.info("Creating product: " + request);
    var calculatedPrice = priceCalculationService.calculatePrice(request.getCurrency(), request.getPrice());
    if (calculatedPrice.isPresent()) {
      return calculatedPrice
        .flatMap(price -> productService.create(new Product(request.getName(), price, request.getCategoryId())))
        .map(this::createProductRetrievedResponse)
        .orElseGet(() -> new ErrorCreatingProductResponse().asResponseEntity());
    } else {
      return new PriceCalculationErrorResponse(request.getCurrency()).asResponseEntity();
    }
  }

  @ApiOperation(value = "Retrieve product by id", httpMethod = "GET")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved a product"),
    @ApiResponse(code = 404, message = "Product not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + PRODUCT_ACCESS_PERMISSION + "')")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "id") Long id) {
    LOGGER.info("Retrieving product for id: " + id);

    return productService.get(id)
      .map(this::createProductRetrievedResponse)
      .orElseGet(() -> new ProductNotFoundResponse(id).asResponseEntity());
  }

  @ApiOperation(value = "Retrieve all products", httpMethod = "GET")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved all products"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + PRODUCT_ACCESS_PERMISSION + "')")
  public Page<ProductResponse> getAllProducts(Pageable pageable) {
    LOGGER.info("Retrieving all products");

    return productService.getAll(pageable)
      .map(product -> new ProductRetrievedResponse(product.getName(), product.getPrice(), product.getCategoryId()));
  }


  @ApiOperation(value = "Update product", httpMethod = "PUT")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully updated a product"),
    @ApiResponse(code = 404, message = "Product not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @PutMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + PRODUCT_ACCESS_PERMISSION + "')")
  public ResponseEntity<ProductResponse> updateProduct(@Validated @RequestBody ProductUpdateRequest request) {
    LOGGER.info("Updating product for id: " + request.getId());

    var calculatedPrice = priceCalculationService.calculatePrice(request.getCurrency(), request.getPrice());
    if (calculatedPrice.isPresent()) {
      return calculatedPrice
        .flatMap(price -> productService.update(request.getId(), new Product(request.getName(), price, request.getCategoryId())))
        .map(this::createProductRetrievedResponse)
        .orElseGet(() -> new ProductNotFoundResponse(request.getId()).asResponseEntity());
    } else {
      return new PriceCalculationErrorResponse(request.getCurrency()).asResponseEntity();
    }
  }

  @ApiOperation(value = "Delete product by id", httpMethod = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully deleted a product"),
    @ApiResponse(code = 404, message = "Product not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + PRODUCT_ACCESS_PERMISSION + "')")
  public ResponseEntity<ProductResponse> deleteProduct(@PathVariable(name = "id") Long id) {
    LOGGER.info("Deleting product for id: " + id);

    return productService.delete(id)
      .map(this::createProductRetrievedResponse)
      .orElseGet(() -> new ProductNotFoundResponse(id).asResponseEntity());
  }

  private ResponseEntity<ProductResponse> createProductRetrievedResponse(Product product) {
    return new ProductRetrievedResponse(product.getName(), product.getPrice(), product.getCategoryId())
      .asResponseEntity();
  }
}
