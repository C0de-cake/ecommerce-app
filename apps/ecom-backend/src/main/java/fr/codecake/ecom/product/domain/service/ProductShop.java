package fr.codecake.ecom.product.domain.service;

import fr.codecake.ecom.product.domain.aggregate.Product;
import fr.codecake.ecom.product.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductShop {

  private ProductRepository productRepository;

  public ProductShop(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<Product> getFeaturedProducts(Pageable pageable) {
    return productRepository.findAllFeaturedProduct(pageable);
  }
}
