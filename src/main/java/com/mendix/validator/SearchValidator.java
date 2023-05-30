package com.mendix.validator;

import com.mendix.search.filter.EqualCriteria;
import com.mendix.search.filter.InCriteria;
import com.mendix.search.filter.LikeCriteria;
import com.mendix.search.filter.NotEqualCriteria;

import java.util.Arrays;
import java.util.List;

public class SearchValidator {
    public static void validateSearchFilter(final String[] criteria) {
        validateKeyValuePair(criteria);

        String[] searchKeyOperation = criteria[0].split("\\.");
        validateKeyOperationPair(criteria, searchKeyOperation);
        validateOperation(searchKeyOperation[searchKeyOperation.length - 1]);

    }

    private static void validateKeyOperationPair(final String[] criteria, final String[] search) {
        if (search.length < 2)
            throw new IllegalArgumentException("Criteria key with operation is not passed properly:" + criteria[0]);
    }

    private static void validateKeyValuePair(final String[] criteria) {
        if (criteria.length != 2)
            throw new IllegalArgumentException("Criteria key/value pair is not passed properly:" + Arrays.toString(criteria));
    }

    private static void validateOperation(final String operation) {
        OPERATORS
                .stream()
                .filter(o -> o.equalsIgnoreCase(operation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong operation is passed in search query:" + operation));
    }

    private static final List<String> OPERATORS = List.of(
            EqualCriteria.EQUAL_OPERATOR,
            NotEqualCriteria.EQUAL_OPERATOR,
            LikeCriteria.LIKE_OPERATOR,
            NotEqualCriteria.EQUAL_OPERATOR,
            InCriteria.IN_OPERATOR
    );
}
