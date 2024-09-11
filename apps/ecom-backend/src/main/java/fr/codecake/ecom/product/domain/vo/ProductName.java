package fr.codecake.ecom.product.domain.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record ProductName(String value) {

  public ProductName {
    Assert.notNull("value", value);
    Assert.field("value", value).minLength(3).maxLength(256);
  }
}
