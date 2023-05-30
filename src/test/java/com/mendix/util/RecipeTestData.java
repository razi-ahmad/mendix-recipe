package com.mendix.util;

import com.mendix.model.*;

import java.util.List;

public class RecipeTestData {

    public static Recipe createRecipe() {
        return Recipe
                .builder()
                .head(buildHead())
                .ingredients(buildIngredient())
                .directions(List.of("test step1"))
                .build();
    }

    public static Head buildHead() {
        return Head.builder().title("test title").categories(List.of("test1", "test2")).yield(1).build();
    }

    public static List<Ingredient> buildIngredient() {
        return List.of(
                Ing.builder().amt(buildAmount()).item("test item").build(),
                Ing.builder().amt(buildAmount()).item("test item2").build()
        );
    }

    public static Amount buildAmount() {
        return Amount
                .builder()
                .quantity("2")
                .unit("kg")
                .build();
    }

    public static Recipe createRecipeWithIngDiv() {
        return Recipe
                .builder()
                .head(buildHead())
                .ingredients(buildIngDiv())
                .directions(List.of("test step1"))
                .build();
    }

    public static List<Ingredient> buildIngDiv() {
        return List.of(
                IngDiv.builder().ing(buildIng()).title("test ingdiv title1").build(),
                IngDiv.builder().ing(buildIng()).title("test ingdiv title2").build()
        );
    }

    public static List<Ing> buildIng() {
        return List.of(
                Ing.builder().amt(buildAmount()).item("test item").build(),
                Ing.builder().amt(buildAmount()).item("test item2").build()
        );
    }

}
