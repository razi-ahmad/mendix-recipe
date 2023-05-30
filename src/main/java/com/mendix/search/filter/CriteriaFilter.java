package com.mendix.search.filter;

import jakarta.persistence.criteria.*;

@FunctionalInterface
public interface CriteriaFilter<T> {

    Predicate toPredicate(String key, String value, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    default boolean evaluate(String operator) {
        return Boolean.FALSE;
    }

    default Join<Object, Object> buildJoins(Root<T> root, String[] tables) {
        Join<Object, Object> join = root.join(tables[0]);
        for (int i = 1; i < tables.length - 2; i++) {
            join = join.join(tables[i], JoinType.LEFT);
        }
        return join;
    }
}