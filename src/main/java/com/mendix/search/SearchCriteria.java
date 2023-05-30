package com.mendix.search;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SearchCriteria {
    private String key;
    private Object value;
    private String operation;
    private Class<?> entity;
}