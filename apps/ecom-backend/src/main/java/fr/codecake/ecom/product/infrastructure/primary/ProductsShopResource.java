package fr.codecake.ecom.product.infrastructure.primary;

import fr.codecake.ecom.product.application.ProductsApplicationService;
import fr.codecake.ecom.product.domain.aggregate.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products-shop")
public class ProductsShopResource {

  private final ProductsApplicationService productsApplicationService;

  public ProductsShopResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  @GetMapping("/featured")
  public ResponseEntity<Page<RestProduct>> getAllFeatured(Pageable pageable) {
    Page<Product> products = productsApplicationService.getFeaturedProducts(pageable);

    PageImpl<RestProduct> restProducts = new PageImpl<>(
      products.getContent().stream().map(RestProduct::fromDomain).toList(),
      pageable,
      products.getTotalElements()
    );
    return ResponseEntity.ok(restProducts);
  }
}
