package com.alirezamehrzad.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank( message = "Old password cannot be empty")
    private String oldPassword;

    @NotBlank( message = "New password cannot be empty")
    private String newPassword;
}
