package fr.codecake.ecom.order.domain.order.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
