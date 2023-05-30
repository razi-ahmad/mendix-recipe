package com.mendix.integration;

import com.mendix.dto.IngDivDto;
import com.mendix.dto.RecipemlDto;
import com.mendix.model.IngDiv;
import com.mendix.model.Recipe;
import com.mendix.repository.RecipeRepository;
import com.mendix.util.RecipeTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest extends BaseControllerTest {
    private static final String BASE_PATH = "/v1/recipes";

    @Autowired
    private RecipeRepository repository;


    @BeforeEach
    public void before() {
        repository.deleteAll();
    }


    @Test
    public void test_list_recipes_with_ing_successfully() throws Exception {
        Recipe model = RecipeTestData.createRecipe();
        model.getIngredients().forEach(i -> i.setRecipe(model));
        List<Recipe> models = List.of(model);

        repository.saveAll(models);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        MvcResult result = performGet(BASE_PATH, params)
                .andExpect(status().isOk())
                .andReturn();

        List<RecipemlDto> recipes = getListFromMvcResult(result);
        assertEquals(models.size(), recipes.size());
        assertEquals(models.get(0).getHead().getTitle(), recipes.get(0).getRecipe().getHead().getTitle());
        assertEquals(models.get(0).getHead().getCategories().size(), recipes.get(0).getRecipe().getHead().getCat().size());
        assertEquals(model.getIngredients().size(), recipes.get(0).getRecipe().getIngredients().size());

    }

    @Test
    public void test_list_recipes_with_ingdiv_successfully() throws Exception {
        Recipe model = RecipeTestData.createRecipeWithIngDiv();
        model.getIngredients().forEach(i -> {
            i.setRecipe(model);
            ((IngDiv) i).getIng().forEach(ing -> ing.setIngDiv((IngDiv) i));
        });
        List<Recipe> models = List.of(model);

        repository.saveAll(models);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        MvcResult result = performGet(BASE_PATH, params)
                .andExpect(status().isOk())
                .andReturn();

        List<RecipemlDto> recipes = getListFromMvcResult(result);
        assertEquals(models.size(), recipes.size());
        assertEquals(models.get(0).getHead().getTitle(), recipes.get(0).getRecipe().getHead().getTitle());
        assertEquals(models.get(0).getHead().getCategories().size(), recipes.get(0).getRecipe().getHead().getCat().size());
        assertEquals(model.getIngredients().size(), recipes.get(0).getRecipe().getIngredients().size());
        IngDiv ingDivModel = (IngDiv) model.getIngredients().get(0);
        IngDivDto ingDivDto = (IngDivDto) recipes.get(0).getRecipe().getIngredients().get(0);
        assertEquals(ingDivModel.getIng().size(), ingDivDto.getIng().size());

    }
}
