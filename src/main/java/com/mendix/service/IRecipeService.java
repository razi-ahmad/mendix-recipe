package com.mendix.service;

import com.mendix.dto.RecipemlDto;

import java.util.List;

public interface IRecipeService {

    void save(RecipemlDto recipemlDto);

    List<RecipemlDto> list(String category);
}
