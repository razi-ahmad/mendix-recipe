package com.mendix.search;

import com.mendix.search.filter.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SearchSpecification<T> implements Specification<T> {


    private final SearchCriteria criteria;
    private final List<CriteriaFilter<T>> filters;


    public SearchSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
        filters = List.of(
                new EqualCriteria<>(),
                new NotEqualCriteria<>(),
                new LikeCriteria<>(),
                new NotLikeCriteria<>(),
                new InCriteria<>()
        );
    }


    @Override
    public Predicate toPredicate(@NotNull Root<T> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder builder) {
        return filters
                .stream()
                .filter(f -> f.evaluate(criteria.getOperation()))
                .findFirst()
                .map(f -> f.toPredicate(criteria.getKey(), criteria.getValue().toString(), root, query, builder))
                .orElse(null);
    }
}