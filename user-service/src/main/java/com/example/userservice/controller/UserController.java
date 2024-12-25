package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final Greeting greeting;

    private final UserService userService;

    private final Environment env;

    private final ModelMapper modelMapper;

    @GetMapping("/health_check")
    public String status() {

        String localServerPort = env.getProperty("local.server.port");
        String serverPort = env.getProperty("server.port");
        String secret = env.getProperty("token.secret");
        String expirationTime = env.getProperty("token.expiration_time");

        return String.format("It's Working in User" +
                        ", port(local.server.port)=%s" +
                        ", port(server.port)=%s" +
                        ", token secret=%s" +
                        ", token expiration time=%s"
                , localServerPort
                , serverPort
                , secret
                , expirationTime
        );
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@Validated @RequestBody RequestUser requestUser) {
        UserDto userDto = UserDto.of(requestUser);

        UserDto user = userService.createUser(userDto);

        return new ResponseEntity<>(ResponseUser.of(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> users = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        users.forEach(v -> {
            result.add(modelMapper.map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser result = modelMapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
