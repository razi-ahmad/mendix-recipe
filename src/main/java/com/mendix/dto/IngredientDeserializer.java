package com.mendix.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDeserializer extends JsonDeserializer<IngredientDto> {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public IngredientDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (!node.has("ing") || !node.has("title")) {
            return buildIngDto(node);
        }

        JsonNode title = node.get("title");
        ArrayNode ingredients = (ArrayNode) node.get("ing");
        List<IngDto> ingDtoList = new ArrayList<>();
        for (JsonNode ingredient : ingredients) {
            IngDto ingDto = buildIngDto(ingredient);
            ingDtoList.add(ingDto);
        }
        return IngDivDto.builder().title(title.asText()).ing(ingDtoList).build();
    }

    private IngDto buildIngDto(JsonNode ingredient) throws JsonProcessingException {
        String content = ingredient.get("amt").toString();
        AmountDto amtDto = OBJECT_MAPPER.readValue(content, AmountDto.class);
        return IngDto.builder().amt(amtDto).item(ingredient.get("item").asText()).build();
    }

}
