package com.mendix.util;

import com.mendix.dto.AmountDto;
import com.mendix.dto.IngDivDto;
import com.mendix.dto.IngDto;
import com.mendix.model.Amount;
import com.mendix.model.Ing;
import com.mendix.model.IngDiv;
import com.mendix.model.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IngredientModelMapper {
    private IngredientModelMapper() {
    }

    public static Ing map(IngDto ing) {
        return Ing
                .builder()
                .amt(map(ing.getAmt()))
                .item(ing.getItem())
                .build();
    }

    private static Amount map(AmountDto amt) {
        return Amount
                .builder()
                .quantity(amt.getQuantity())
                .unit(amt.getUnit())
                .build();
    }


    public static Ingredient map(IngDivDto ingDiv) {
        return IngDiv
                .builder()
                .title((ingDiv).getTitle())
                .ing(
                        Optional.of(ingDiv.getIng())
                                .map(IngredientModelMapper::map)
                                .orElse(null)
                )
                .build();

    }

    private static List<Ing> map(List<IngDto> ingDtoList) {
        return ingDtoList
                .stream()
                .map(IngredientModelMapper::map
                )
                .collect(Collectors.toList());
    }
}
