package com.mendix.service;

import com.mendix.dto.RecipemlDto;
import com.mendix.model.IngDiv;
import com.mendix.model.Recipe;
import com.mendix.repository.RecipeRepository;
import com.mendix.util.RecipeDtoMapper;
import com.mendix.util.RecipeModelMapper;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<RecipemlDto> list() {
            return repository
                    .findAll()
                    .stream().map(RecipeDtoMapper::map)
                    .collect(Collectors.toList());
    }
}
