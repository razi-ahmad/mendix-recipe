package com.mendix.controller;

import com.mendix.dto.GenericDto;
import com.mendix.dto.RecipemlDto;
import com.mendix.service.IRecipeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@OpenAPIDefinition(info = @Info(title = "Recipe Controller", description = "List recipes"))
public class RecipeController {
    private final IRecipeService service;

    @Autowired
    public RecipeController(IRecipeService service) {
        this.service = service;
    }

    @Operation(summary = "List all recipes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericDto.class))})
    })
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecipemlDto>> getRecipes() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }
}
