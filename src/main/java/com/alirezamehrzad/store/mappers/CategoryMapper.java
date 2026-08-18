package com.alirezamehrzad.store.mappers;

import com.alirezamehrzad.store.dtos.CategoryDto;
import com.alirezamehrzad.store.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);
    CategoryDto toDto(Category category);
    List<CategoryDto> toDtos(List<Category> categories);
}
