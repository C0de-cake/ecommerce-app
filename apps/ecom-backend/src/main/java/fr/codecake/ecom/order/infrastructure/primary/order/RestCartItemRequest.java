package fr.codecake.ecom.order.infrastructure.primary.order;

import fr.codecake.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import fr.codecake.ecom.order.domain.order.aggregate.DetailCartItemRequestBuilder;
import fr.codecake.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestCartItemRequest(UUID publicId, long quantity) {

  public static DetailCartItemRequest to(RestCartItemRequest item) {
    return DetailCartItemRequestBuilder.detailCartItemRequest()
      .productId(new PublicId(item.publicId()))
      .quantity(item.quantity())
      .build();
  }

  public static RestCartItemRequest from(DetailCartItemRequest detailCartItemRequest) {
    return RestCartItemRequestBuilder.restCartItemRequest()
      .publicId(detailCartItemRequest.productId().value())
      .quantity(detailCartItemRequest.quantity())
      .build();
  }

  public static List<DetailCartItemRequest> to(List<RestCartItemRequest> items) {
    return items.stream().map(RestCartItemRequest::to).toList();
  }

  public static List<RestCartItemRequest> from(List<DetailCartItemRequest> items) {
    return items.stream().map(RestCartItemRequest::from).toList();
  }
}
