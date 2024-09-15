package fr.codecake.ecom.order.infrastructure.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Builder
public class OrderedProductEntityPk implements Serializable {

  @ManyToOne
  @JoinColumn(name = "fk_order", nullable = false)
  private OrderEntity order;

  @Column(name = "fk_product", nullable = false)
  private UUID productPublicId;

  public OrderedProductEntityPk() {
  }

  public OrderedProductEntityPk(OrderEntity order, UUID productPublicId) {
    this.order = order;
    this.productPublicId = productPublicId;
  }

  public OrderEntity getOrder() {
    return order;
  }

  public void setOrder(OrderEntity order) {
    this.order = order;
  }

  public UUID getProductPublicId() {
    return productPublicId;
  }

  public void setProductPublicId(UUID productPublicId) {
    this.productPublicId = productPublicId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderedProductEntityPk that)) return false;
    return Objects.equals(productPublicId, that.productPublicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(productPublicId);
  }
}
