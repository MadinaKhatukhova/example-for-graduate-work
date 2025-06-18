package ru.skypro.homework.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.NotEditUserPasswordException;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
//    private final ImageService service;
//
    @Autowired
    private UserService userService;
//
//    @Autowired
//    public UserController(ImageService service) {
//        this.service = service;
//    }

    @PostMapping("/set_password")
//    @Operation(summary = "Обновление пароля")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "OK"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized"),
//            @ApiResponse(responseCode = "403", description = "Forbidden")
//    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            // Здесь заглушка, тк пользователя не получаем(Лола поправить)
            Long userId = 1L;
            userService.updatePassword(userId, newPasswordDTO);
            return ResponseEntity.ok().build();
        } catch (NotEditUserPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/me")
//    @Operation(summary = "Получение информации об авторизованном пользователе")
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "OK",
//                    content = @Content(schema = @Schema(implementation = UserDTO.class))
//            ),
//            @ApiResponse(responseCode = "401",
//                    description = "Unauthorized"
////            )
//    })
    public ResponseEntity<UserDTO> getUser() {
        // Здесь заглушка, тк пользователя не получаем(Лола поправить)
        Long userId = 1L;
        return ResponseEntity.ok(userService.findUserDTO(userId));
    }

    @PatchMapping("/me")
//    @Operation(summary = "Обновление информации об авторизованном пользователе")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser) {
        //заглушка тк не знаем какой пользователь(Лола поправить)
        Long userId = 1L;
        return ResponseEntity.ok(userService.updateUser(userId, updateUser));
    }

//        @PatchMapping("/me/image")
////    @Operation(summary = "Обновление аватара авторизованного пользователя")
////    @ApiResponses({
////            @ApiResponse(responseCode = "200", description = "OK"),
////            @ApiResponse(responseCode = "401", description = "Unauthorized")
////    })
//        public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) {
//
//        Long userId = 1L;
//        try {
//            service.uploadImage(userId, image);
//        } catch (NotSaveAvatarEx e) {
//            log.warn("NotSaveAvatarException in save image");
//            return ResponseEntity.badRequest().build();
//        } catch (IOException e) {
//            log.warn("IOException in save image");
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
//    }
}