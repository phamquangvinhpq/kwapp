package com.ttcsolutions.kwapp.core.model.entity;

import com.dslplatform.json.JsonAttribute;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@MappedSuperclass
@Data
@Accessors(chain = true)
public abstract class BaseEntity<T> {
  @Column(columnDefinition = "timestamp default now()")
  @JsonAttribute(name = "created_at")
  private OffsetDateTime createdAt;
  @JsonAttribute(name = "updated_at")
  private OffsetDateTime updatedAt;

  protected abstract T self();

  public T setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return self();
  }

  public T setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return self();
  }
}