package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.UserPrincipal;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdController {
    @Autowired
    private AdsService adsService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        List<AdDTO> ads = adsService.getAllAds();
        AdsDTO response = new AdsDTO();
        response.setResults(ads);
        response.setCount(ads.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AdDTO> addAd (@RequestPart("properties") CreateOrUpdateAdDTO properties,
                                        @RequestPart("image") MultipartFile image,
                                        @AuthenticationPrincipal UserPrincipal authentication) throws IOException {
        AdDTO ad = adsService.addAd(properties, image, authentication.getUser());
        return new ResponseEntity<>(ad, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddInfo(@PathVariable int id) {
        ExtendedAdDTO ad = adsService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdd(@PathVariable int id) {
        adsService.removeAd(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAddInfo(@PathVariable int id,
                                               @RequestBody CreateOrUpdateAdDTO ad) {
        AdDTO updateAd = adsService.updateAd(id, ad);
        return ResponseEntity.ok(updateAd);
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAuthUserAdds(Authentication authentication) {
        UserEntity userEntity = userService.findByUsername(authentication.getName());
        AdsDTO ads = adsService.getAdsForLoggedInUser(userEntity);
        return ResponseEntity.ok(ads);
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> updateAddPicture(@PathVariable int  id,
                                                 @RequestBody MultipartFile image) {
        adsService.updateAdImage(id, image);
        return ResponseEntity.ok().build();
    }

}

