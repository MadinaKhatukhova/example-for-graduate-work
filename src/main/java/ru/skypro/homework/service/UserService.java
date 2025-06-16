package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;

public interface UserService {

     UserEntity findUser(Integer userId);

     UserDTO getUserDTO(UserEntity userEntity);

    UserEntity getUserById(Integer id);

    void saveUser(UserEntity userEntity);

    UserEntity findByUsername(String email);

    UpdateUserDTO updateUser(Integer userId, UpdateUserDTO updateUser);

    UserDTO findUserDTO(Integer userId);

    void updatePassword(Integer userId, NewPasswordDTO newPasswordDTO);




}
