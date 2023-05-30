package com.mendix.util;

import com.mendix.dto.*;
import com.mendix.model.Head;
import com.mendix.model.Ingredient;
import com.mendix.model.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipeModelMapper {
    private RecipeModelMapper() {
    }

    public static Recipe map(RecipeDto recipe) {
        return Recipe
                .builder()
                .head(map(recipe.getHead()))
                .ingredients(
                        Optional.of(recipe.getIngredients())
                                .map(RecipeModelMapper::ingredientListMap)
                                .orElse(null)
                )
                .directions(recipe.getStep())
                .build();
    }

    private static Head map(HeadDto head) {
        return Head
                .builder()
                .title(head.getTitle())
                .categories(head.getCat())
                .yield(head.getYield())
                .build();
    }

    private static List<Ingredient> ingredientListMap(List<IngredientDto> ingredientDtos) {
        return ingredientDtos
                .stream()
                .map(ingredient -> {
                            if (ingredient instanceof IngDivDto) {
                                return IngredientModelMapper.map((IngDivDto) ingredient);
                            } else {
                                return IngredientModelMapper.map((IngDto) ingredient);
                            }
                        }

                )
                .collect(Collectors.toList());
    }
}
