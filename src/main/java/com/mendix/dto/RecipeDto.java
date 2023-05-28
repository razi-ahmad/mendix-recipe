package com.mendix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto implements Serializable {

    @JsonProperty(value = "head")
    @XmlElement(name = "head")
    @NotNull(message = "Head is required")
    private HeadDto head;

    @JsonProperty(value = "ingredients")
    @XmlElements({
            @XmlElement(name = "ing", type = IngDto.class),
            @XmlElement(name = "ing-div", type = IngDivDto.class)

    })
    @XmlElementWrapper(name = "ingredients")
    @NotNull(message = "Ingredients are required")
    private List<IngredientDto> ingredients;


    @JsonProperty(value = "directions")
    @XmlElementWrapper(name = "directions")
    @XmlElement(name = "step")
    @NotNull(message = "Steps of Direction are required.")
    private List<String> step;
}