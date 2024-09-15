package fr.codecake.ecom.order.domain.order.aggregate;

import fr.codecake.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
