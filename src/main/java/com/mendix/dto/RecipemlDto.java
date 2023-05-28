package com.mendix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "recipeml")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RecipemlDto {
    @XmlElement(name = "recipe")
    @NotNull(message = "Recipe is required")
    private RecipeDto recipe;
}
