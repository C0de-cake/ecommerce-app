package fr.codecake.ecom.order.domain.order.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record OrderQuantity(long value) {

  public OrderQuantity {
    Assert.field("value", value).positive();

  }
}
