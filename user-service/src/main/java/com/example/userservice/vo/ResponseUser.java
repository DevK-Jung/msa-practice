package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    public static ResponseUser of(UserDto userDto) {
        return new ResponseUser(userDto.getEmail(), userDto.getName(), userDto.getUserId());
    }
}
