package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {
    @Autowired
    private AdsService adsService;

    @GetMapping
    public ResponseEntity<List<AdDTO>> getAllAds() {
        List<AdDTO> ads = adsService.getAllAds();
        return ResponseEntity.ok(ads);
    }

    @PostMapping
    public ResponseEntity<AdDTO> postAdd(@RequestParam("properties")CreateOrUpdateAdDTO properties,
                                         Authentication authentication) throws IOException {
        AdDTO ad = adsService.addAd(properties, authentication);
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
    public ResponseEntity<List<AdDTO>> getAuthUserAdds() {
        List<AdDTO> ads = adsService.getAdsForLoggedInUser();
        return ResponseEntity.ok(ads);
    }

//    @PatchMapping("/{id}/image")
//    public ResponseEntity<Void> updateAddPicture() {
//        return ResponseEntity.ok().build();
//    }

}
