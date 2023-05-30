package com.mendix.service;

import com.mendix.dto.RecipemlDto;
import com.mendix.exception.NotFoundException;
import com.mendix.model.IngDiv;
import com.mendix.model.Recipe;
import com.mendix.repository.RecipeRepository;
import com.mendix.search.SearchCriteria;
import com.mendix.search.SpecificationBuilder;
import com.mendix.util.Constant;
import com.mendix.util.RecipeDtoMapper;
import com.mendix.util.RecipeModelMapper;
import com.mendix.validator.SearchValidator;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeService implements IRecipeService {

    private final RecipeRepository repository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.repository = recipeRepository;
    }

    @Override
    @TransactionScoped
    public void save(RecipemlDto recipemlDto) {
        Recipe recipe = RecipeModelMapper.map(recipemlDto.getRecipe());
        recipe.getIngredients().forEach(ingredient -> {
            ingredient.setRecipe(recipe);
            if (ingredient instanceof IngDiv) {
                ((IngDiv) ingredient).getIng().forEach(
                        ing -> ing.setIngDiv((IngDiv) ingredient));
            }
        });
        repository.save(recipe);
    }

    @Override
    public List<RecipemlDto> list(String search) {
        if (Objects.isNull(search)) {

            return repository
                    .findAll()
                    .stream().map(RecipeDtoMapper::map)
                    .collect(Collectors.toList());
        }
        return filterRecipes(search);

    }

    private List<RecipemlDto> filterRecipes(final String search) {
        String[] filters = search.split(Constant.AND);
        List<SearchCriteria> searchCriteria = new ArrayList<>();

        Arrays.stream(filters).forEach(f -> {
            String[] criteria = f.split(Constant.EQUAL);

            SearchValidator.validateSearchFilter(criteria);
            searchCriteria.add(buildSearchCriteria(criteria));
        });


        SpecificationBuilder<Recipe> builder = new SpecificationBuilder<>();
        searchCriteria.forEach(builder::with);

        Specification<Recipe> specification = builder
                .build()
                .orElseThrow(() -> new NotFoundException("criteria not found"));
        return repository.findAll(specification)
                .stream()
                .map(RecipeDtoMapper::map)
                .collect(Collectors.toList());
    }

    private static SearchCriteria buildSearchCriteria(String[] criteria) {
        String key = criteria[0].substring(0, criteria[0].lastIndexOf(Constant.DOT));
        String operation = criteria[0].substring(criteria[0].lastIndexOf(Constant.DOT) + 1);
        String value = criteria[1];
        return new SearchCriteria(key, value, operation, Recipe.class);
    }

}
