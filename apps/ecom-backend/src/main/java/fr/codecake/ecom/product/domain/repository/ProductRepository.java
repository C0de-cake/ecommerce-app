package fr.codecake.ecom.product.domain.repository;

import fr.codecake.ecom.product.domain.aggregate.Product;
import fr.codecake.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

  Product save(Product productToCreate);

  Page<Product> findAll(Pageable pageable);

  int delete(PublicId publicId);
}
