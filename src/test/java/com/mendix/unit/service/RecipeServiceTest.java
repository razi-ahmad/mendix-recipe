package com.mendix.unit.service;

import com.mendix.dto.RecipemlDto;
import com.mendix.model.Recipe;
import com.mendix.repository.RecipeRepository;
import com.mendix.service.RecipeService;
import com.mendix.util.RecipeDtoTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @InjectMocks
    private RecipeService underTest;

    @Mock
    private RecipeRepository repository;

    @Test
    void test_save_with_ing() {
        RecipemlDto request = RecipeDtoTestData.createRecipeWithIng();
        underTest.save(request);
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Recipe.class));
    }

    @Test
    void test_save_with_ingdiv() {
        RecipemlDto request = RecipeDtoTestData.createRecipeWithIngDiv();
        underTest.save(request);
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any(Recipe.class));
    }
}