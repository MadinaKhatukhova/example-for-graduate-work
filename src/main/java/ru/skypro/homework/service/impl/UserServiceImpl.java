package ru.skypro.homework.service.impl;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;

import ru.skypro.homework.exception.NotEditUserPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    //Находит пользователя по его идентификатору.
    public UserEntity findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    //Преобразует сущность пользователя в DTO.
    public UserDTO getUserDTO(UserEntity userEntity) {
        return userMapper.userEntityToUserDTO(userEntity);
    }

    //Находит пользователя по его идентификатору.
    public UserEntity getUserById(Long id) {
        UserEntity byUserId = userRepository.findByUserId(id);
        if (byUserId == null) {
            throw new RuntimeException();
        }
        return byUserId;
    }

    //Сохраняет пользователя.
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    //Находит пользователя по его email.
    public UserEntity findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    //Обновляет информацию о пользователе.
    public UpdateUserDTO updateUser(UserEntity userEntity, UpdateUserDTO updateUser) {
        if (updateUser.getFirstName() != null) {
            userEntity.setFirstName(updateUser.getFirstName());
        }
        if (updateUser.getLastName() != null) {
            userEntity.setLastName(updateUser.getLastName());
        }
        if (updateUser.getPhone() != null) {
            userEntity.setPhone(updateUser.getPhone());
        }
        saveUser(userEntity);
        return updateUser;
    }

    //Находит DTO пользователя по его идентификатору.
    public UserDTO findUserDTO(Long userId) {
        return getUserDTO(findUser(userId));
    }

    //Обновляет пароль пользователя.
    public void updatePassword(Long userId, NewPasswordDTO newPasswordDTO) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Проверяем текущий пароль
        if (!passwordEncoder.matches(newPasswordDTO.getCurrentPassword(), userEntity.getPassword())) {
            throw new NotEditUserPasswordException("Current password is incorrect");
        }

        // Устанавливаем и кодируем новый пароль
        userEntity.setPassword(passwordEncoder.encode(newPasswordDTO.getNewPassword()));
        userRepository.save(userEntity);
    }

}