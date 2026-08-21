package com.alirezamehrzad.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull( message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 10, message = "Quantity must be less than 10")
    private Integer quantity;

}
