package com.mendix.search.filter;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class NotEqualCriteria<T> implements CriteriaFilter<T> {
    public static final String EQUAL_OPERATOR = "ne";


    @Override
    public Predicate toPredicate(String key, String value, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!key.contains(".")) return builder.equal(root.<String>get(key), value);

        String[] keys = key.split("\\.");
        return builder.notEqual(buildJoins(root, keys).get(keys[keys.length - 1]), value);
    }

    @Override
    public boolean evaluate(String operator) {
        return EQUAL_OPERATOR.equalsIgnoreCase(operator);
    }
}
