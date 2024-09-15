package fr.codecake.ecom.order.domain.order.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record StripeSessionId(String value) {

  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
