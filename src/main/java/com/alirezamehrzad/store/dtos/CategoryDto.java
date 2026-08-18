package com.alirezamehrzad.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters")
    private String name;
}
