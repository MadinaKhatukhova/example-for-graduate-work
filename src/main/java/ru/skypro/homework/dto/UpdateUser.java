package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String phone;

}
