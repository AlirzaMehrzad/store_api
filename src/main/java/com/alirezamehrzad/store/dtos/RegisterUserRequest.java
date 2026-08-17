package com.alirezamehrzad.store.dtos;

import com.alirezamehrzad.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank( message = "Name cannot be empty")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotBlank( message = "Email cannot be empty")
    @Email( message = "Email must be valid")
    @Lowercase( message = "Email must be lowercase")
    private String email;

    @NotBlank( message = "Password cannot be empty")
    @Size(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
    private String password;
}
