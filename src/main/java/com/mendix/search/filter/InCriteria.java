package com.mendix.search.filter;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class InCriteria<T> implements CriteriaFilter<T> {
    public static final String IN_OPERATOR = "in";


    @Override
    public Predicate toPredicate(String key, String value, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<String> values = Arrays.asList(StringUtils.splitPreserveAllTokens(value, ","));
        if (!key.contains(".")) return builder.in(root.get(key)).value(values);

        String[] keys = key.split("\\.");
        return builder.in(buildJoins(root, keys).join(keys[keys.length - 1])).value(values);
    }

    @Override
    public boolean evaluate(String operator) {
        return IN_OPERATOR.equalsIgnoreCase(operator);
    }
}
