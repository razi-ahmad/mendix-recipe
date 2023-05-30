package com.mendix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "amount")
public class Amount extends BaseEntity {
    @Column(name = "qty")
    private String quantity;

    @Column(name = "unit")
    private String unit;

    @OneToOne(mappedBy = "amt")
    private Ing ing;

}