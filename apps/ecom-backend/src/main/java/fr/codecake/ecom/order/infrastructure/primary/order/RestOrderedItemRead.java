package fr.codecake.ecom.order.infrastructure.primary.order;

import fr.codecake.ecom.order.domain.order.aggregate.OrderedProduct;
import org.jilt.Builder;

import java.util.List;

@Builder
public record RestOrderedItemRead(long quantity,
                                  double price,
                                  String name) {

  public static RestOrderedItemRead from(OrderedProduct orderedProduct) {
    return RestOrderedItemReadBuilder.restOrderedItemRead()
      .name(orderedProduct.getProductName().value())
      .quantity(orderedProduct.getQuantity().value())
      .price(orderedProduct.getPrice().value())
      .build();
  }

  public static List<RestOrderedItemRead> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(RestOrderedItemRead::from).toList();
  }

}
