package com.mendix.unit.config;

import com.mendix.config.DataLoader;
import com.mendix.dto.RecipemlDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Optional;

import static com.mendix.config.DataLoader.DIRECTORY;


@ExtendWith(MockitoExtension.class)
class DataLoaderTest {

    private static final String QUALIFIED_FILE_NAME_PATH = "/" + DIRECTORY + "/" + "30_Minute_Chili.xml";

    @InjectMocks
    private DataLoader underTest;

    @Mock
    protected ResourceLoader resourceLoader;


    @Test
    void test_run() throws Exception {
        Resource resource = Mockito.mock(Resource.class);
        File file = Mockito.mock(File.class);

        Mockito.when(resourceLoader.getResource("classpath:" + DIRECTORY)).thenReturn(resource);
        Mockito.when(resource.isFile()).thenReturn(Boolean.TRUE);
        Mockito.when(resource.getFile()).thenReturn(file);
        Mockito.when(resource.getFile().isDirectory()).thenReturn(Boolean.TRUE);

        underTest.run(null);
        Mockito.verify(resourceLoader, Mockito.times(1)).getResource(ArgumentMatchers.anyString());
        Mockito.verify(resource.getFile(), Mockito.times(1)).list();

    }

    @Test
    void load_data() {
        Optional<RecipemlDto> result = underTest.loadData(QUALIFIED_FILE_NAME_PATH);
        Assert.isTrue(result.isPresent(), "Recipe is not loaded");
        Assert.notNull(result.get().getRecipe(), "Recipe is null");
        Assert.noNullElements(result.get().getRecipe().getIngredients(), "Ingredients are empty");
        Assert.hasText(result.get().getRecipe().getHead().getTitle(), "30 Minute Chili");
    }

    @Test
    void load_data_null_file_name() {
        Optional<RecipemlDto> result = underTest.loadData(null);
        Assertions.assertFalse(result.isPresent());
    }
}