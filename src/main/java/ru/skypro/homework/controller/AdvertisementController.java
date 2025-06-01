package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdvertisementController {
    @GetMapping
    public ResponseEntity<?> getAllAds(){

    }

    @PostMapping
    public ResponseEntity<?> postAdd(){

    }

    @GetMapping
    public ResponseEntity<?> getAddInfo(){

    }

    @DeleteMapping
    public ResponseEntity<?> deleteAdd(){

    }

    @PatchMapping
    public ResponseEntity<?> updateAddInfo(){

    }

    @GetMapping
    public ResponseEntity<?> getAuthUserAdds(){

    }

    @PatchMapping
    public ResponseEntity<?> updateAddPicture(){

    }

}
