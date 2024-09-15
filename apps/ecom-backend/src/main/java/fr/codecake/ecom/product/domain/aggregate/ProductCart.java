package fr.codecake.ecom.product.domain.aggregate;

import fr.codecake.ecom.product.domain.vo.ProductBrand;
import fr.codecake.ecom.product.domain.vo.ProductName;
import fr.codecake.ecom.product.domain.vo.ProductPrice;
import fr.codecake.ecom.product.domain.vo.PublicId;
import fr.codecake.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public class ProductCart {

  private ProductName name;

  private ProductPrice price;

  private ProductBrand brand;

  private Picture picture;

  private PublicId publicId;

  public ProductCart() {
  }

  public ProductCart(ProductName name, ProductPrice price, ProductBrand brand,
                     Picture picture, PublicId publicId) {
    assertFields(name, price, brand, picture, publicId);
    this.name = name;
    this.price = price;
    this.brand = brand;
    this.picture = picture;
    this.publicId = publicId;
  }

  private void assertFields(ProductName name, ProductPrice price, ProductBrand brand,
                     Picture picture, PublicId publicId) {
    Assert.notNull("brand", brand);
    Assert.notNull("name", name);
    Assert.notNull("price", price);
    Assert.notNull("picture", picture);
    Assert.notNull("publicId", publicId);
  }

  public static ProductCart from(Product product) {
    return ProductCartBuilder.productCart()
      .name(product.getName())
      .price(product.getPrice())
      .brand(product.getProductBrand())
      .picture(product.getPictures().stream().findFirst().orElseThrow())
      .publicId(product.getPublicId())
      .build();
  }

  public ProductName getName() {
    return name;
  }

  public ProductPrice getPrice() {
    return price;
  }

  public ProductBrand getBrand() {
    return brand;
  }

  public Picture getPicture() {
    return picture;
  }

  public PublicId getPublicId() {
    return publicId;
  }
}
