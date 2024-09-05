package fr.codecake.ecom.order.domain.user.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record UserImageUrl(String value) {

  public UserImageUrl {
    Assert.field("value", value).maxLength(1000);
  }
}
