package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;

@RestController
@RequestMapping("/ads")
public class AdController {
    @GetMapping
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> postAdd(@RequestBody AdDTO adDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddInfo(@RequestParam int id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdd(@RequestParam int id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAddInfo(@RequestParam int id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthUserAdds(@RequestBody AdsDTO adsDTO) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateAddPicture() {
        return ResponseEntity.ok().build();
    }

}
