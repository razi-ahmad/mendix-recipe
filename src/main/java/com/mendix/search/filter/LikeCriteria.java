package com.mendix.search.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LikeCriteria<T> implements CriteriaFilter<T> {
    public static final String LIKE_OPERATOR = "li";
    private static final String PERCENTAGE_OPERATOR = "%";


    @Override
    public Predicate toPredicate(String key, String value, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!key.contains("."))
            return builder.like(root.get(key), PERCENTAGE_OPERATOR.concat(value).concat(PERCENTAGE_OPERATOR));

        String[] keys = key.split("\\.");
        return builder.like(buildJoins(root, keys).get(keys[keys.length - 1]), PERCENTAGE_OPERATOR.concat(value).concat(PERCENTAGE_OPERATOR));
    }

    @Override
    public boolean evaluate(String operator) {
        return LIKE_OPERATOR.equalsIgnoreCase(operator);
    }
}
