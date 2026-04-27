package com.example.vsms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerRequest(
        @NotBlank(message = "Customer name is required")
        String name,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9+\\-\\s]{7,20}$", message = "Phone number is invalid")
        String phone,

        @Email(message = "Email address is invalid")
        String email,

        String address
) {
}
