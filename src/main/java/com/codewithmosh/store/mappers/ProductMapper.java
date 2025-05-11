package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ProductDTO;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO product);

    @Mapping(target = "id", ignore = true)
    void update(ProductDTO request, @MappingTarget Product product);
}
