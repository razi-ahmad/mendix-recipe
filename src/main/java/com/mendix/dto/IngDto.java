package com.mendix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngDto extends IngredientDto {
    @JsonProperty(value = "amt")
    @XmlElement(name = "amt")
    private AmountDto amt;

    @JsonProperty(value = "item")
    @XmlElement(name = "item")
    @NotEmpty(message = "Ingredient item is required")
    private String item;
}