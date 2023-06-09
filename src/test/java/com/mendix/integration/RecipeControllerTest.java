package com.mendix.integration;

import com.mendix.dto.IngDivDto;
import com.mendix.dto.RecipemlDto;
import com.mendix.model.IngDiv;
import com.mendix.model.Recipe;
import com.mendix.repository.RecipeRepository;
import com.mendix.util.RecipeDtoTestData;
import com.mendix.util.RecipeTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void test_create_recipe_successfully() throws Exception {
        RecipemlDto request = RecipeDtoTestData.createRecipeWithIng();
        performPost(BASE_PATH, request)
                .andExpect(status().isCreated())
                .andReturn();

        assertFalse(repository.findAll().isEmpty());
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
        assertEquals(model.getHead().getTitle(), recipes.get(0).getRecipe().getHead().getTitle());
        assertEquals(model.getHead().getCategories().size(), recipes.get(0).getRecipe().getHead().getCat().size());
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

    @Test
    public void test_search_recipe_by_criteria_title_successfully() throws Exception {
        Recipe model = RecipeTestData.createRecipe();
        model.getIngredients().forEach(i -> i.setRecipe(model));
        List<Recipe> models = List.of(model);
        repository.saveAll(models);

        String searchCriteria = "head.title.eq=test title";


        MvcResult result = performGet(BASE_PATH + "?search=" + searchCriteria)
                .andExpect(status().isOk())
                .andReturn();

        List<Recipe> optionalRecipe = repository.findAll();


        List<RecipemlDto> listRecipeList = getListFromMvcResult(result);
        Assertions.assertEquals(optionalRecipe.size(), listRecipeList.size());

    }

    @Test
    public void test_search_recipe_by_criteria_category_successfully() throws Exception {
        Recipe model = RecipeTestData.createRecipe();
        model.getIngredients().forEach(i -> i.setRecipe(model));
        List<Recipe> models = List.of(model);
        repository.saveAll(models);

        String searchCriteria = "head.categories.in=test1";


        MvcResult result = performGet(BASE_PATH + "?search=" + searchCriteria)
                .andExpect(status().isOk())
                .andReturn();

        List<Recipe> optionalRecipe = repository.findAll();


        List<RecipemlDto> listRecipeList = getListFromMvcResult(result);
        Assertions.assertEquals(optionalRecipe.size(), listRecipeList.size());
    }

    @Test
    public void test_search_recipe_by_criteria_fails() throws Exception {
        performGet(BASE_PATH + "/search")
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void test_get_categories() throws Exception {
        Recipe model = RecipeTestData.createRecipe();
        model.getIngredients().forEach(i -> i.setRecipe(model));
        List<Recipe> models = List.of(model);
        repository.saveAll(models);

        MvcResult result = performGet(BASE_PATH + "/heads/categories" )
                .andExpect(status().isOk())
                .andReturn();

        List<String> categories = getListFromMvcResult(result);
        Assertions.assertEquals(categories.size(), model.getHead().getCategories().size());

    }
}
