package fr.codecake.ecom.product.infrastructure.secondary.entity;

import fr.codecake.ecom.product.domain.aggregate.Category;
import fr.codecake.ecom.product.domain.aggregate.CategoryBuilder;
import fr.codecake.ecom.product.domain.vo.CategoryName;
import fr.codecake.ecom.product.domain.vo.PublicId;
import fr.codecake.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_category")
@Builder
public class CategoryEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
  @SequenceGenerator(name = "categorySequence", sequenceName = "product_category_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  @OneToMany(mappedBy = "category")
  private Set<ProductEntity> products;

  public CategoryEntity() {
  }

  public CategoryEntity(Long id, String name, UUID publicId, Set<ProductEntity> products) {
    this.id = id;
    this.name = name;
    this.publicId = publicId;
    this.products = products;
  }

  public static CategoryEntity from(Category category) {
    CategoryEntityBuilder categoryEntityBuilder = CategoryEntityBuilder.categoryEntity();

    if (category.getDbId() != null) {
      categoryEntityBuilder.id(category.getDbId());
    }

    return categoryEntityBuilder
      .name(category.getName().value())
      .publicId(category.getPublicId().value())
      .build();
  }

  public static Category to(CategoryEntity categoryEntity) {
    return CategoryBuilder.category()
      .dbId(categoryEntity.getId())
      .name(new CategoryName(categoryEntity.getName()))
      .publicId(new PublicId(categoryEntity.getPublicId()))
      .build();
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
