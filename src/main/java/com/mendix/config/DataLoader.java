package com.mendix.config;

import com.mendix.dto.RecipemlDto;
import com.mendix.service.IRecipeService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationRunner {

    public static final String DIRECTORY = "recipes";

    private final ResourceLoader resourceLoader;
    private final IRecipeService recipeService;

    @Autowired
    public DataLoader(ResourceLoader resourceLoader,
                      IRecipeService recipeService) {
        this.resourceLoader = resourceLoader;
        this.recipeService = recipeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:" + DIRECTORY);
        if (resource.isFile() && resource.getFile().isDirectory()) {

            Optional.ofNullable(resource.getFile().list()).ifPresent(files -> Arrays.asList(files).forEach(f -> {
                Optional<RecipemlDto> recipe = loadData("/" + DIRECTORY + "/" + f);
                recipe.ifPresent(this::storeData);
            }));
        }
    }

    public Optional<RecipemlDto> loadData(String fileName) {
        if (Objects.isNull(fileName)) return Optional.empty();

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            if (Objects.isNull(is)) {
                return Optional.empty();
            }
            RecipemlDto recipeml = (RecipemlDto) xmlToObject(new InputStreamReader(is));
            return Optional.ofNullable(recipeml);
        } catch (IOException ex) {
            log.warn("Error reading file resource:{} with the message:{}", fileName, ex.getMessage());
            return Optional.empty();
        } catch (IllegalArgumentException ex) {
            log.warn("Error reading file:{} with the message:{}", fileName, ex.getMessage());
            return Optional.empty();
        }
    }

    private void storeData(RecipemlDto recipe) {
        recipeService.save(recipe);
    }

    private static Object xmlToObject(Reader reader) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RecipemlDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Error while converting xml to object", e);
        }
    }
}