package ru.skypro.homework.dto;

import lombok.*;

@Data
public class NewPasswordDTO {
    private String currentPassword;
    private String newPassword;

}
