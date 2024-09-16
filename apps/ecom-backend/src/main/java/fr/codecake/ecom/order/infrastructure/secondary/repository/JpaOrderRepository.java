package fr.codecake.ecom.order.infrastructure.secondary.repository;

import fr.codecake.ecom.order.domain.order.vo.OrderStatus;
import fr.codecake.ecom.order.infrastructure.secondary.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

  @Modifying
  @Query("UPDATE OrderEntity  order SET order.status = :orderStatus WHERE order.publicId = :orderPublicId")
  void updateStatusByPublicId(OrderStatus orderStatus, UUID orderPublicId);

  Optional<OrderEntity> findByStripeSessionId(String stripeSessionId);

  Page<OrderEntity> findAllByUserPublicId(UUID userPublicId, Pageable pageable);

}
