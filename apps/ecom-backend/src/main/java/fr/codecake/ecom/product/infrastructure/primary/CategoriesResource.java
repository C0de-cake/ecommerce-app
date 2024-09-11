package fr.codecake.ecom.product.infrastructure.primary;

import fr.codecake.ecom.product.application.ProductsApplicationService;
import fr.codecake.ecom.product.domain.aggregate.Category;
import fr.codecake.ecom.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

import static fr.codecake.ecom.product.infrastructure.primary.ProductsAdminResource.ROLE_ADMIN;

@RestController
@RequestMapping("/api/categories")
public class CategoriesResource {

  private static final Logger log = LoggerFactory.getLogger(CategoriesResource.class);
  private final ProductsApplicationService productsApplicationService;

  public CategoriesResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<RestCategory> save(@RequestBody RestCategory restCategory) {
    Category categoryDomain = RestCategory.toDomain(restCategory);
    Category categorySaved = productsApplicationService.createCategory(categoryDomain);
    return ResponseEntity.ok(RestCategory.fromDomain(categorySaved));
  }

  @DeleteMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<UUID> delete(UUID publicId) {
    try {
      PublicId deletedCategoryPublicId = productsApplicationService.deleteCategory(new PublicId(publicId));
      return ResponseEntity.ok(deletedCategoryPublicId.value());
    } catch (EntityNotFoundException enff) {
      log.error("Could not delete category with id {}", publicId, enff);
      ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, enff.getMessage());
      return ResponseEntity.of(problemDetail).build();
    }
  }

  @GetMapping
  public ResponseEntity<Page<RestCategory>> findAll(Pageable pageable) {
    Page<Category> categories = productsApplicationService.findAllCategory(pageable);
    PageImpl<RestCategory> restCategories = new PageImpl<>(
      categories.getContent().stream().map(RestCategory::fromDomain).toList(),
      pageable,
      categories.getTotalElements()
    );
    return ResponseEntity.ok(restCategories);
  }

}
