package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.UserPrincipal;
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
        UserDTO userDTO = userService.getUserDTO(userService.findByUsername(username));
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser,
                                                    Authentication authentication) {
        String userName = authentication.getName();
        return ResponseEntity.ok(
                userService.updateUser(
                        userService.findByUsername(userName),
                        updateUser)
        );
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image,
                                             @AuthenticationPrincipal UserPrincipal principal) throws IOException {
        try {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is empty");
            }

            imageService.uploadImage(principal.getUser().getUserId(), image);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            log.warn("Invalid image: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            log.error("Failed to save image", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}