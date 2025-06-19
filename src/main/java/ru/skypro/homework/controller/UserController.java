package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.NotEditUserPasswordException;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final ImageService imageService;
  
    private final UserService userService;


    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDTO newPasswordDTO,
                                            Authentication authentication) {
        try {
            String username = authentication.getName();
            userService.updatePassword(Long.valueOf(username), newPasswordDTO);
            return ResponseEntity.ok().build();
        } catch (NotEditUserPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(userService.findUserDTO(Long.valueOf(username)));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser,
                                                    Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(userService.updateUser(Long.valueOf(username), updateUser));
    }
    @PatchMapping("/me/image")
//    @Operation(summary = "Обновление аватара авторизованного пользователя")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image,
                                             Authentication authentication) throws IOException {
        try {
            String username = authentication.getName();
            imageService.uploadImage(Long.valueOf(username), image);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("NotSaveAvatarException in save image");
            return ResponseEntity.badRequest().build();
        }
    }

}