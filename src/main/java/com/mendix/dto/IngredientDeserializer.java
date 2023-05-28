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

import static com.mendix.util.Constant.*;

public class IngredientDeserializer extends JsonDeserializer<IngredientDto> {



    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public IngredientDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (!node.has(INGREDIENT_FIELD) || !node.has(TITLE_FIELD)) {
            return buildIngDto(node);
        }

        JsonNode title = node.get(TITLE_FIELD);
        ArrayNode ingredients = (ArrayNode) node.get(INGREDIENT_FIELD);
        List<IngDto> ingDtoList = new ArrayList<>();
        for (JsonNode ingredient : ingredients) {
            IngDto ingDto = buildIngDto(ingredient);
            ingDtoList.add(ingDto);
        }
        return IngDivDto.builder().title(title.asText()).ing(ingDtoList).build();
    }

    private IngDto buildIngDto(JsonNode ingredient) throws JsonProcessingException {
        String content = ingredient.get(AMOUNT_FIELD).toString();
        AmountDto amtDto = OBJECT_MAPPER.readValue(content, AmountDto.class);
        return IngDto.builder().amt(amtDto).item(ingredient.get(ITEM_FIELD).asText()).build();
    }

}
