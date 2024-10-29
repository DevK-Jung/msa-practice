package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {
    @Email
    @Size(min = 2, message = "Email not be less than two characters")
    @NotNull(message = "Email cannot be null")
    private String email;
    @Size(min = 2, message = "Name not be less than two characters")
    @NotNull(message = "Name cannot be null")
    private String name;
    @Size(min = 8, message = "password must be equal or grater than 8 characters")
    @NotNull(message = "password cannot be null")
    private String pwd;
}
