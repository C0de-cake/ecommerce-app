package fr.codecake.ecom.order.domain.order.repository;

import fr.codecake.ecom.order.domain.order.aggregate.Order;
import fr.codecake.ecom.order.domain.order.aggregate.StripeSessionInformation;
import fr.codecake.ecom.order.domain.order.vo.OrderStatus;
import fr.codecake.ecom.product.domain.vo.PublicId;

import java.util.Optional;

public interface OrderRepository {

  void save(Order order);

  void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId);

  Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

}
