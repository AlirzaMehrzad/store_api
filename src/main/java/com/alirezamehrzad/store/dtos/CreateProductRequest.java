package com.alirezamehrzad.store.dtos;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private String categoryId;
    private String price;
}
