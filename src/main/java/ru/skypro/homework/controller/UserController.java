package ru.skypro.homework.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        // Логика обновления пароля
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        // Логика получения информации о пользователе
        return ResponseEntity.ok(new User());
    }

    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUser updateUser) {
        // Логика обновления информации о пользователе
        return ResponseEntity.ok(new User());
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image) {
        // Логика обновления аватара
        return ResponseEntity.ok().build();
    }
}