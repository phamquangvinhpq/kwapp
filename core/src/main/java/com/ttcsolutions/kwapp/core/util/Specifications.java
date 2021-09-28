package com.ttcsolutions.kwapp.core.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public class Specifications {
    private Specifications() {
    }

    private static <E> Specification<E> search(List<SingularAttribute<E, String>> attributes, String value) {
        if (value == null || attributes.isEmpty()) {
            return null;
        }
        return doSearch(attributes, value);
    }

    private static <E> Specification<E> doSearch(List<SingularAttribute<E, String>> attributes, String value) {
        var attr = doContain(attributes.get(0), value);
        for (int i = 1; i < attributes.size(); ++i) {
            attr = attr.or(doContain(attributes.get(i), value));
        }
        return attr;
    }

    private static <E, T> Specification<E> equal(SingularAttribute<E, T> attribute, Object value) {
        if (value == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get(attribute), value);
    }

    private static <E, T> Specification<E> in(SingularAttribute<E, T> attribute, List<T> values) {
        if (values == null) {
            return null;
        }
        return (root, query, builder) -> root.get(attribute).in(values);
    }

    private static <E> Specification<E> contain(SingularAttribute<E, String> attribute, String value) {
        if (value == null) {
            return null;
        }
        return doContain(attribute, value);
    }

    private static <E> Specification<E> doContain(SingularAttribute<E, String> attribute, String value) {
        return (root, query, builder) -> builder.like(root.get(attribute), "%" + value + "%");
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
