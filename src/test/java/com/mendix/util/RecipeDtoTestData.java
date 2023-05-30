package com.mendix.util;

import com.mendix.dto.*;

import java.util.List;

public class RecipeDtoTestData {

    public static RecipemlDto createRecipeWithIng() {
        return RecipemlDto
                .builder()
                .recipe(RecipeDtoTestData.buildRecipeIng())
                .build();
    }

    public static RecipeDto buildRecipeIng() {
        return RecipeDto
                .builder()
                .head(buildHead())
                .ingredients(buildIngredient())
                .step(List.of("test step1"))
                .build();
    }

    public static HeadDto buildHead() {
        return HeadDto.builder().title("test title").cat(List.of("test1", "test2")).yield(1).build();
    }

    public static List<IngredientDto> buildIngredient() {
        return List.of(
                IngDto.builder().amt(buildAmount()).item("test item").build(),
                IngDto.builder().amt(buildAmount()).item("test item2").build()
        );
    }

    public static AmountDto buildAmount() {
        return AmountDto
                .builder()
                .quantity("2")
                .unit("kg")
                .build();
    }

    public static RecipemlDto createRecipeWithIngDiv() {
        return RecipemlDto
                .builder()
                .recipe(RecipeDtoTestData.buildRecipeIngDiv())
                .build();
    }

    public static RecipeDto buildRecipeIngDiv() {
        return RecipeDto
                .builder()
                .head(buildHead())
                .ingredients(buildIngDiv())
                .step(List.of("test step1"))
                .build();
    }

    public static List<IngredientDto> buildIngDiv() {
        return List.of(
                IngDivDto.builder().ing(buildIngDto()).title("test ingdiv title1").build(),
                IngDivDto.builder().ing(buildIngDto()).title("test ingdiv title2").build()
        );
    }

    public static List<IngDto> buildIngDto() {
        return List.of(
                IngDto.builder().amt(buildAmount()).item("test item").build(),
                IngDto.builder().amt(buildAmount()).item("test item2").build()
        );
    }

}
