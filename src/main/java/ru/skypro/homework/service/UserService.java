package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;

import java.util.Optional;

public interface UserService {

    /**
     * Поиск пользователя по id
     * @param userId
     * @return userEntity
     */
    UserEntity findUser(Long userId);

    /**
     * Возвращает DTO пользователя
     * @param userEntity
     * @return userDTO
     */
    UserDTO getUserDTO(UserEntity userEntity);

    /**
     * Возвращает пользователя
     * @param id
     * @return userEntity
     */
    UserEntity getUserById(Long id);

    /**
     * Сохраняет пользователя
     * @param userEntity
     */
    void saveUser(UserEntity userEntity);

    /**
     * Поиск пользователя по email
     * @param email
     * @return userEntity
     */
    UserEntity findByUsername(String email);

    /**
     * Обновляет пользователя
     * @param userEntity
     * @param updateUser
     * @return updateUserDTO
     */
    UpdateUserDTO updateUser(UserEntity userEntity, UpdateUserDTO updateUser);

    /**
     * Поиск DTO пользователя по его id
     * @param userId
     * @return userDTO
     */
    UserDTO findUserDTO(Long userId);

    /**
     * Обновляет пароль пользователя
     * @param userId
     * @param newPasswordDTO
     */
    void updatePassword(Long userId, NewPasswordDTO newPasswordDTO);

}
