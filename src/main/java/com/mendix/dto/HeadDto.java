package com.mendix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
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
public class HeadDto implements Serializable {
    @JsonProperty(value = "title")
    @XmlElement(name = "title")
    @NotEmpty(message = "Recipe title is required")
    @Size(min = 2, max = 100, message = "The length of title must be between 2 and 100 characters.")
    private String title;

    @JsonProperty(value = "categories")
    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "cat")
    @NotNull(message = "Categories are required.")
    private List<String> cat;

    @JsonProperty(value = "yield")
    @XmlElement(name = "yield")
    @NotNull(message = "Yield is required")
    private Integer yield;
}