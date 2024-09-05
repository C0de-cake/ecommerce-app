package fr.codecake.ecom.order.domain.user.vo;

import fr.codecake.ecom.shared.error.domain.Assert;

public record AuthorityName(String name) {

  public AuthorityName {
    Assert.field("name", name).notNull();
  }
}
