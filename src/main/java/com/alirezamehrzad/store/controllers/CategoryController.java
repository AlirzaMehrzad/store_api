package com.alirezamehrzad.store.controllers;

import com.alirezamehrzad.store.dtos.CategoryDto;
import com.alirezamehrzad.store.mappers.CategoryMapper;
import com.alirezamehrzad.store.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        var categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<CategoryDto> categoryDtos = categoryMapper.toDtos(categories);

        return ResponseEntity.ok(categoryDtos);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable Byte id
    ){
        var category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto request
    ){
        var category = categoryMapper.toEntity(request);
        var savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.toDto(savedCategory));
    }

}
