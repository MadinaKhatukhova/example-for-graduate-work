package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.NotEditUserPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findUser_shouldReturnUser_whenUserExists() {
        // Arrange
        Integer userId = 1;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        UserEntity result = userService.findUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findUser_shouldThrowException_whenUserNotFound() {
        // Arrange
        Integer userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findUser(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserDTO_shouldReturnMappedDTO() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1);

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1);

        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedDTO);

        // Act
        UserDTO result = userService.getUserDTO(userEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userMapper, times(1)).userEntityToUserDTO(userEntity);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        // Arrange
        Integer userId = 1;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUserId(userId);

        when(userRepository.findByUserId(userId)).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // Arrange
        Integer userId = 999;
        when(userRepository.findByUserId(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    void saveUser_shouldCallRepositorySave() {
        // Arrange
        UserEntity userToSave = new UserEntity();
        userToSave.setUserId(1);

        // Act
        userService.saveUser(userToSave);

        // Assert
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void findByUsername_shouldReturnUser_whenUserExists() {
        // Arrange
        String email = "user@example.com";
        UserEntity expectedUser = new UserEntity();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.findByUsername(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void updateUser_shouldUpdateFieldsAndReturnDTO() {
        // Arrange
        Integer userId = 1;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(userId);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setPhone("123");

        UpdateUserDTO updateDTO = new UpdateUserDTO();
        updateDTO.setFirstName("New");
        updateDTO.setLastName("Name");
        updateDTO.setPhone("456");

        when(userRepository.findByUserId(userId)).thenReturn(existingUser);

        // Act
        UpdateUserDTO result = userService.updateUser(userId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New", existingUser.getFirstName());
        assertEquals("Name", existingUser.getLastName());
        assertEquals("456", existingUser.getPhone());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_shouldUpdateOnlyNonNullFields() {
        // Arrange
        Integer userId = 1;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(userId);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setPhone("123");

        UpdateUserDTO updateDTO = new UpdateUserDTO();
        updateDTO.setFirstName("New");
        // lastName и phone остаются null

        when(userRepository.findByUserId(userId)).thenReturn(existingUser);

        // Act
        UpdateUserDTO result = userService.updateUser(userId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New", existingUser.getFirstName());
        assertEquals("Name", existingUser.getLastName()); // осталось прежним
        assertEquals("123", existingUser.getPhone()); // осталось прежним
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void findUserDTO_shouldReturnUserDTO() {
        // Arrange
        Integer userId = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedDTO);

        // Act
        UserDTO result = userService.findUserDTO(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).userEntityToUserDTO(userEntity);
    }

    @Test
    void updatePassword_shouldThrowException_whenCurrentPasswordDoesNotMatch() {
        // Arrange
        Integer userId = 1;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setPassword("oldPassword");

        NewPasswordDTO passwordDTO = new NewPasswordDTO();
        passwordDTO.setCurrentPassword("wrongPassword");
        passwordDTO.setNewPassword("newPassword");

        when(userRepository.findByUserId(userId)).thenReturn(userEntity);

        // Act & Assert
        assertThrows(NotEditUserPasswordException.class,
                () -> userService.updatePassword(userId, passwordDTO));
        verify(userRepository, never()).save(any());
    }
}