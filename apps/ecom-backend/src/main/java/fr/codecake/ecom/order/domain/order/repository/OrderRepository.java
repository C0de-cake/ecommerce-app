package fr.codecake.ecom.order.domain.order.repository;

import fr.codecake.ecom.order.domain.order.aggregate.Order;
import fr.codecake.ecom.order.domain.order.aggregate.StripeSessionInformation;
import fr.codecake.ecom.order.domain.order.vo.OrderStatus;
import fr.codecake.ecom.order.domain.user.vo.UserPublicId;
import fr.codecake.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

  void save(Order order);

  void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId);

  Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

  Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable);

  Page<Order> findAll(Pageable pageable);

}
