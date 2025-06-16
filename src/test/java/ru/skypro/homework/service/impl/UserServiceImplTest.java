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
        Long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUserId((long) Math.toIntExact(userId));

        when(userRepository.findById(Math.toIntExact(userId))).thenReturn(Optional.of(expectedUser));

        // Act
        UserEntity result = userService.findUser(Math.toIntExact(userId));

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(userRepository, times(1)).findById(Math.toIntExact(userId));
    }

    @Test
    void findUser_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(Math.toIntExact(userId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findUser(Math.toIntExact(userId)));
        verify(userRepository, times(1)).findById(Math.toIntExact(userId));
    }

    @Test
    void getUserDTO_shouldReturnMappedDTO() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1L);

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1L);

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
        Long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setUserId((long) Math.toIntExact(userId));

        when(userRepository.findByUserId((long) Math.toIntExact(userId))).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.getUserById(Math.toIntExact(userId));

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(userRepository, times(1)).findByUserId((long) Math.toIntExact(userId));
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findByUserId((long) Math.toIntExact(userId))).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(Math.toIntExact(userId)));
        verify(userRepository, times(1)).findByUserId((long) Math.toIntExact(userId));
    }

    @Test
    void saveUser_shouldCallRepositorySave() {
        // Arrange
        UserEntity userToSave = new UserEntity();
        userToSave.setUserId(1L);

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
        Long userId = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId((long) Math.toIntExact(userId));
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setPhone("123");

        UpdateUserDTO updateDTO = new UpdateUserDTO();
        updateDTO.setFirstName("New");
        updateDTO.setLastName("Name");
        updateDTO.setPhone("456");

        when(userRepository.findByUserId((long) Math.toIntExact(userId))).thenReturn(existingUser);

        // Act
        UpdateUserDTO result = userService.updateUser(Math.toIntExact(userId), updateDTO);

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
        Long userId = 1L;
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId((long) Math.toIntExact(userId));
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setPhone("123");

        UpdateUserDTO updateDTO = new UpdateUserDTO();
        updateDTO.setFirstName("New");
        // lastName и phone остаются null

        when(userRepository.findByUserId((long) Math.toIntExact(userId))).thenReturn(existingUser);

        // Act
        UpdateUserDTO result = userService.updateUser(Math.toIntExact(userId), updateDTO);

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
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId((long) Math.toIntExact(userId));

        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId((long) Math.toIntExact(userId));

        when(userRepository.findById(Math.toIntExact(userId))).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUserDTO(userEntity)).thenReturn(expectedDTO);

        // Act
        UserDTO result = userService.findUserDTO(Math.toIntExact(userId));

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(Math.toIntExact(userId));
        verify(userMapper, times(1)).userEntityToUserDTO(userEntity);
    }

    @Test
    void updatePassword_shouldThrowException_whenCurrentPasswordDoesNotMatch() {
        // Arrange
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId((long) Math.toIntExact(userId));
        userEntity.setPassword("oldPassword");

        NewPasswordDTO passwordDTO = new NewPasswordDTO();
        passwordDTO.setCurrentPassword("wrongPassword");
        passwordDTO.setNewPassword("newPassword");

        when(userRepository.findByUserId((long) Math.toIntExact(userId))).thenReturn(userEntity);

        // Act & Assert
        assertThrows(NotEditUserPasswordException.class,
                () -> userService.updatePassword(Math.toIntExact(userId), passwordDTO));
        verify(userRepository, never()).save(any());
    }
}