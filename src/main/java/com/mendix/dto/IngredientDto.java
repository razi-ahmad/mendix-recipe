package com.mendix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.ToString;

import java.io.Serializable;

@ToString
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({IngDivDto.class, IngDto.class})
@JsonDeserialize(using = IngredientDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class IngredientDto implements Serializable {

}
