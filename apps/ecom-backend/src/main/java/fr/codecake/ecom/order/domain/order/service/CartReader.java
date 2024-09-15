package fr.codecake.ecom.order.domain.order.service;

import fr.codecake.ecom.order.domain.order.aggregate.DetailCartResponse;
import fr.codecake.ecom.order.domain.order.aggregate.DetailCartResponseBuilder;
import fr.codecake.ecom.product.domain.aggregate.Product;
import fr.codecake.ecom.product.domain.aggregate.ProductCart;

import java.util.List;

public class CartReader {

  public CartReader() {
  }

  public DetailCartResponse getDetails(List<Product> products) {
    List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
