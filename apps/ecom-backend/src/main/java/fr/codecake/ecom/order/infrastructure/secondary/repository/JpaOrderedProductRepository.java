package fr.codecake.ecom.order.infrastructure.secondary.repository;

import fr.codecake.ecom.order.infrastructure.secondary.entity.OrderedProductEntity;
import fr.codecake.ecom.order.infrastructure.secondary.entity.OrderedProductEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, OrderedProductEntityPk> {

}
