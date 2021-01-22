package dev.alvo.productinventory.api.controller;

import dev.alvo.productinventory.api.reponse.category.CategoryNotFoundResponse;
import dev.alvo.productinventory.api.reponse.category.CategoryResponse;
import dev.alvo.productinventory.api.reponse.category.CategoryRetrievedResponse;
import dev.alvo.productinventory.api.reponse.category.ErrorCreatingCategoryResponse;
import dev.alvo.productinventory.api.request.category.CategoryCreateRequest;
import dev.alvo.productinventory.api.request.category.CategoryUpdateRequest;
import dev.alvo.productinventory.model.Category;
import dev.alvo.productinventory.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Set;

import static dev.alvo.productinventory.config.SecurityConfiguration.CATEGORY_ACCESS_PERMISSION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/private/category")
public class CategoryController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @ApiOperation(value = "Create new category", httpMethod = "POST")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully created new category"),
    @ApiResponse(code = 400, message = "Error creating new category"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + CATEGORY_ACCESS_PERMISSION + "')")
  public ResponseEntity<CategoryResponse> createCategory(@Validated @RequestBody CategoryCreateRequest request) {
    LOGGER.info("Creating category: " + request);

    return categoryService.create(new Category(request.getName(), Set.of()))
      .map(this::createCategoryRetrievedResponse)
      .orElseGet(() -> new ErrorCreatingCategoryResponse().asResponseEntity());
  }

  @ApiOperation(value = "Retrieve category by id", httpMethod = "GET")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved a category"),
    @ApiResponse(code = 404, message = "Category not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + CATEGORY_ACCESS_PERMISSION + "')")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "id") Long id) {
    LOGGER.info("Retrieving category for id: " + id);

    return categoryService.get(id)
      .map(this::createCategoryRetrievedResponse)
      .orElseGet(() -> new CategoryNotFoundResponse(id).asResponseEntity());
  }

  @ApiOperation(value = "Update category", httpMethod = "PUT")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully updated a category"),
    @ApiResponse(code = 404, message = "Category not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @PutMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + CATEGORY_ACCESS_PERMISSION + "')")
  public ResponseEntity<CategoryResponse> updateCategory(@Validated @RequestBody CategoryUpdateRequest request) {
    LOGGER.info("Updating category for id: " + request.getId());

    return categoryService.get(request.getId())
        .flatMap(existing -> categoryService.update(request.getId(), new Category(request.getName(), Set.of())))
        .map(this::createCategoryRetrievedResponse)
        .orElseGet(() -> new ErrorCreatingCategoryResponse().asResponseEntity());
  }

  @ApiOperation(value = "Delete category by id", httpMethod = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully deleted a category"),
    @ApiResponse(code = 404, message = "Category not found for specified id"),
    @ApiResponse(code = 500, message = "Internal Server Error")
  })
  @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('" + CATEGORY_ACCESS_PERMISSION + "')")
  public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable(name = "id") Long id) {
    LOGGER.info("Deleting category for id: " + id);

    return categoryService.markForDeletion(id)
      .map(this::createCategoryRetrievedResponse)
      .orElseGet(() -> new CategoryNotFoundResponse(id).asResponseEntity());
  }

  private ResponseEntity<CategoryResponse> createCategoryRetrievedResponse(Category category) {
    return new CategoryRetrievedResponse(category.getName(), category.getProducts()).asResponseEntity();
  }
}
