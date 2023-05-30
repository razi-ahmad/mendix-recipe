package com.mendix.util;

import com.mendix.dto.*;
import com.mendix.model.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecipeDtoMapper {
    private RecipeDtoMapper() {
    }

    public static RecipemlDto map(Recipe recipe) {
        return RecipemlDto
                .builder()
                .recipe(mapRecipe(recipe))
                .build();
    }

    public static RecipeDto mapRecipe(Recipe recipe) {
        return RecipeDto
                .builder()
                .head(map(recipe.getHead()))
                .ingredients(
                        Objects.requireNonNull(Optional.ofNullable(recipe.getIngredients())
                                .map(RecipeDtoMapper::ingredientListMap)
                                .orElse(null))
                )
                .step(recipe.getDirections())
                .build();
    }

    private static IngDto map(Ing ing) {
        return IngDto
                .builder()
                .amt(map(ing.getAmt()))
                .item(ing.getItem())
                .build();
    }

    private static AmountDto map(Amount amt) {
        return AmountDto
                .builder()
                .quantity(amt.getQuantity())
                .unit(amt.getUnit())
                .build();
    }

    private static HeadDto map(Head head) {
        return HeadDto
                .builder()
                .title(head.getTitle())
                .cat(head.getCategories())
                .yield(head.getYield())
                .build();
    }

    private static List<IngredientDto> ingredientListMap(List<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(ingredient -> {
                            if (ingredient instanceof IngDiv) {
                                return map((IngDiv) ingredient);
                            } else {
                                return map((Ing) ingredient);
                            }
                        }

                )
                .collect(Collectors.toList());
    }

    private static IngredientDto map(IngDiv ingDiv) {
        return IngDivDto
                .builder()
                .title((ingDiv).getTitle())
                .ing(
                        Objects.requireNonNull(Optional.ofNullable(ingDiv.getIng()).map(RecipeDtoMapper::map).orElse(null))
                )
                .build();

    }

    private static List<IngDto> map(List<Ing> ingList) {
        return ingList
                .stream()
                .map(RecipeDtoMapper::map
                )
                .collect(Collectors.toList());
    }
}
