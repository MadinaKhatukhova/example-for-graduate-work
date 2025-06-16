package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdsServiceImpl implements AdsService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final AdMapper adMapper;

    @Override
    public AdDTO getAdDTO(AdEntity adEntity) {
        return adMapper.toDto(adEntity);
    }

    @Override
    public AdEntity getAd(AdDTO adDTO) {
        return adMapper.toEntity(adDTO);
    }

    @Override
    public List<AdDTO> getAllAds() {
        List<AdEntity> adEntities = adRepository.findAll();
        return adEntities.stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, Authentication authentication) {
        AdEntity adEntity = adMapper.createOrUpdateAdToAdEntity(properties);
        // Сохранение изображения и установка пути к изображению в сущность
        adEntity.setImage(image.getOriginalFilename());
        AdEntity savedAd = adRepository.save(adEntity);
        return adMapper.toDto(savedAd);
    }

    @Override
    public ExtendedAdDTO getAdById(int id) {
        return null;
    }

    @Override
    public ExtendedAdDTO getAdById(long id) {
        Optional<AdEntity> adEntityOptional = adRepository.findById(id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            return adMapper.adEntityToExtendedAd(adEntity); // Преобразование в ExtendedAd
        } else {
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public void removeAd(int id) {
        adRepository.deleteById((long) id);
    }

    @Override
    public AdDTO updateAd(int id, CreateOrUpdateAdDTO ad) {
        Optional<AdEntity> adEntityOptional = adRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            adMapper.createOrUpdateAdToAdEntity(ad);
            AdEntity updatedAd = adRepository.save(adEntity);
            return adMapper.toDto(updatedAd); // Преобразование и возврат обновленного объявления
        } else {
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public void updateAdImage(int id, MultipartFile image) {
        Optional<AdEntity> adEntityOptional = adRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            adEntity.setImage(image.getOriginalFilename());
            adRepository.save(adEntity);
        } else {
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public List<AdDTO> getAdsForLoggedInUser() {
        return null;
    }

    @Override
    public boolean isAuthorAd(String email, Integer adId) {
        AdEntity adEntity = adRepository.findById(Long.valueOf(adId)).orElseThrow(RuntimeException::new);
        return adEntity.getEmail().equals(email);
    }

    @Override
    public AdEntity findById(Integer id) {
        return adRepository.findById(Long.valueOf(id)).get();
    }
}
