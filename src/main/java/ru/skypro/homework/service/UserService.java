package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;

public interface UserService {

     UserEntity findUser(Long userId);

     UserDTO getUserDTO(UserEntity userEntity);

    UserEntity getUserById(Long id);

    void saveUser(UserEntity userEntity);

    UserEntity findByUsername(String email);

    UpdateUserDTO updateUser(Long userId, UpdateUserDTO updateUser);

    UserDTO findUserDTO(Long userId);

    void updatePassword(Long userId, NewPasswordDTO newPasswordDTO);




}
