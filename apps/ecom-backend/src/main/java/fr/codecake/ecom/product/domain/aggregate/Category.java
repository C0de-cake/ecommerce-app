package fr.codecake.ecom.product.domain.aggregate;

import fr.codecake.ecom.product.domain.vo.CategoryName;
import fr.codecake.ecom.product.domain.vo.PublicId;
import fr.codecake.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public class Category {

  private final CategoryName name;

  private Long dbId;
  private PublicId publicId;

  public Category(CategoryName name, Long dbId, PublicId publicId) {
    assertMandatoryFields(name);
    this.name = name;
    this.dbId = dbId;
    this.publicId = publicId;
  }

  private void assertMandatoryFields(CategoryName categoryName) {
    Assert.notNull("name", categoryName);
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  public CategoryName getName() {
    return name;
  }

  public Long getDbId() {
    return dbId;
  }

  public PublicId getPublicId() {
    return publicId;
  }
}
