package com.example.userservice.dto;

import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDate createAt;
    private String encryptedPwd;

    private List<ResponseOrder> orders;

    private UserDto(String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
    }

    public static UserDto of(RequestUser requestUser) {
        return new UserDto(
                requestUser.getEmail(),
                requestUser.getName(),
                requestUser.getPwd()
        );
    }
}
