package fr.codecake.ecom.product.infrastructure.secondary.entity;

import fr.codecake.ecom.product.domain.aggregate.Picture;
import fr.codecake.ecom.product.domain.aggregate.PictureBuilder;
import fr.codecake.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "product_picture")
@Builder
public class PictureEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictureSequence")
  @SequenceGenerator(name = "pictureSequence", sequenceName = "product_picture_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Lob
  @Column(name = "file", nullable = false)
  private byte[] file;

  @Column(name = "file_content_type", nullable = false)
  private String mimeType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_fk", nullable = false)
  private ProductEntity product;

  public PictureEntity() {
  }

  public PictureEntity(Long id, byte[] file, String mimeType, ProductEntity product) {
    this.id = id;
    this.file = file;
    this.mimeType = mimeType;
    this.product = product;
  }

  public static PictureEntity from(Picture picture) {
    return PictureEntityBuilder.pictureEntity()
      .file(picture.file())
      .mimeType(picture.mimeType())
      .build();
  }

  public static Picture to(PictureEntity pictureEntity) {
    return PictureBuilder.picture()
      .file(pictureEntity.getFile())
      .mimeType(pictureEntity.getMimeType())
      .build();
  }

  public static Set<PictureEntity> from(List<Picture> pictures) {
    return pictures.stream().map(PictureEntity::from).collect(Collectors.toSet());
  }

  public static List<Picture> to(Set<PictureEntity> pictureEntities) {
    return pictureEntities.stream().map(PictureEntity::to).collect(Collectors.toList());
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PictureEntity that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
