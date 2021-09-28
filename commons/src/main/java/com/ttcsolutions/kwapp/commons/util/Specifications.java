package com.ttcsolutions.kwapp.commons.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;

public class Specifications {

  private static <E, T> Specification<E> equal(SingularAttribute<E, T> attribute, Object value) {
    if (value == null) {
      return null;
    }
    return (root, query, builder) -> builder.equal(root.get(attribute), value);
  }

  private static <E, T extends Comparable<? super T>> Specification<E> greaterThanOrEqualTo(SingularAttribute<E, T> attribute, T value) {
    if (value == null) {
      return null;
    }
    return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(attribute), value);
  }

  private static <E, T extends Comparable<? super T>> Specification<E> lessThan(SingularAttribute<E, T> attribute, T value) {
    if (value == null) {
      return null;
    }
    return (root, query, builder) -> builder.lessThan(root.get(attribute), value);
  }
}
