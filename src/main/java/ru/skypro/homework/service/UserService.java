package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;

import java.util.Optional;

public interface UserService {

    //метод
    UserEntity findUser(Long userId);

    //метод получения ДТО пользователя
    UserDTO getUserDTO(UserEntity userEntity);

    //метод получения пользователя по его id
    UserEntity getUserById(Long id);

    //метод сохранения пользователя
    void saveUser(UserEntity userEntity);

    //метод нахождения пользователя по его email
    UserEntity findByUsername(String email);

    //метод обновления пользователя
    UpdateUserDTO updateUser(UserEntity userEntity, UpdateUserDTO updateUser);

    //метод нахождения ДТО пользователя по его id
    UserDTO findUserDTO(Long userId);

    //метод обновления пароля пользователя
    void updatePassword(Long userId, NewPasswordDTO newPasswordDTO);

}
