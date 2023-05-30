/**
 *
 */
package com.mendix.search;

import com.mendix.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecificationBuilder<T> {

    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    public SpecificationBuilder<T> with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

    public Specification<T> build(Specification<T> result) {
        if (params.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new SearchSpecification<>(param));
        }
        int index = 0;
        if (result == null) {
            result = specs.get(0);
            index = 1;
        }
        for (int i = index; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }

    public Optional<Specification<Recipe>> build() {
        if (params.size() == 0) return Optional.empty();

        Specification<Recipe> result = new SearchSpecification<>(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteria = params.get(i);
            result = Specification.where(result).and(new SearchSpecification<>(criteria));
        }
        return Optional.of(result);
    }
}