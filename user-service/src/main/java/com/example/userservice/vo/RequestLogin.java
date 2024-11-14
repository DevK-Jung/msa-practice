package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @Email
    @NotBlank(message = "Email can not be blank")
    @Size(min = 2, message = "Email not be less than two characters")
    private String email;
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, message = "Password must be equals or than 8 characters")
    private String password;
}
