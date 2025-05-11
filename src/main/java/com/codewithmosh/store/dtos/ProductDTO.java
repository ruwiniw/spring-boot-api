package com.codewithmosh.store.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @JsonProperty("category_id")
    private Long categoryId;
}
