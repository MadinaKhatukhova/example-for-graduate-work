package ru.skypro.homework.dto;

import lombok.*;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private String image;

}
