package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private List<ResponseOrder> orders;

    public static ResponseUser of(UserDto userDto) {
        return new ResponseUser(userDto.getEmail(), userDto.getName(), userDto.getUserId());
    }

    public ResponseUser(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }
}
