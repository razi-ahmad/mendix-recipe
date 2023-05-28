package com.mendix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngDivDto extends IngredientDto {
    @JsonProperty(value = "title")
    @XmlElement(name = "title")
    private String title;

    @JsonProperty(value = "ing")
    @XmlElement(name = "ing")
    @NotNull(message = "Ingredients are required")
    private List<IngDto> ing;
}
