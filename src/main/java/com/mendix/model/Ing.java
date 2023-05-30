package com.mendix.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "ingredient_id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ing extends Ingredient {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private Amount amt;

    @Column(name = "item")
    private String item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingdiv_id")
    private IngDiv ingDiv;
}