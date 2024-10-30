package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    public UserEntity(String email, String name, String userId, String encryptedPwd) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPwd = encryptedPwd;
    }

    public static UserEntity from(UserDto userDto) {
        return new UserEntity(userDto.getEmail(), userDto.getName(), userDto.getUserId(), userDto.getEncryptedPwd());
    }
}
