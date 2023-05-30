package com.mendix.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "head")
public class Head extends BaseEntity {
    @Column(name = "title")
    private String title;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "head_id"))
    @Column(name = "cat", nullable = false)
    private List<String> categories;

    @Column(name = "yield")
    private Integer yield;

    @OneToOne(mappedBy = "head")
    private Recipe recipe;
}