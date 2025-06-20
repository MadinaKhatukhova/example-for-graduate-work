package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdsServiceImpl implements AdsService {

    private static final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final AdMapper adMapper;

    @Override
    public AdDTO getAdDTO(AdEntity adEntity) {
        AdDTO ad = adMapper.adEntityToAdDTO(adEntity);
        logger.debug("getAdDTO - {}", ad);
        return ad;
    }

    @Override
    public AdEntity getAd(AdDTO adDTO) {
        AdEntity ad = adMapper.adDTOToAdEntityWithoutId(adDTO);
        logger.debug("getAd - {}", ad);
        return ad;
    }

    @Override
    public List<AdDTO> getAllAds() {
        List<AdEntity> adEntities = adRepository.findAll();
        List<AdDTO> ads = adEntities.stream()
                .map(adMapper::adEntityToAdDTO)
                .collect(Collectors.toList());
        logger.debug("getAllAds - {}", ads);
        return ads;
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, UserEntity userEntity) {
        AdEntity adEntity = adMapper.createOrUpdateAdToAdEntity(properties);
        adEntity.setUserEntity(userEntity);
        ImageEntity imageEntity = new  ImageEntity();
        try {
            imageEntity.setData(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageEntity.setMediaType(image.getContentType());
        imageEntity.setFileSize(image.getSize());
        adEntity.setImageEntity(imageEntity);

        // Сохранение изображения и установка пути к изображению в сущность
        adEntity.setImage(image.getOriginalFilename());
        AdEntity savedAd = adRepository.save(adEntity);
        AdDTO ad = adMapper.adEntityToAdDTO(savedAd);
        logger.debug("addAd");
        return ad;
    }

    @Override
    public CommentEntity addCommentToAdd(Integer id, CreateOrUpdateCommentDTO comment) {
        CommentEntity newComment = new CommentEntity();
        AdEntity byId;
        try {
            logger.debug("byId - {}", findById(id.longValue()));
            byId = findById(id.longValue());
        } catch (Exception e) {
            logger.debug("Не найдено объявление по его id");
            throw new RuntimeException("Не найдено объявление по его id");
        }
        newComment.setAdEntity(byId);
        newComment.setText(comment.getText());
        CommentEntity commentEntity = commentRepository.save(newComment);
        logger.debug("addCommentToAdd - {}", commentEntity);
        return commentEntity;
    }

    @Override
    public ExtendedAdDTO getAdById(int id) {
        Optional<AdEntity> adEntityOptional = adRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            ExtendedAdDTO extendedAdDTO = adMapper.adEntityToExtendedAd(adEntity); // Преобразование в ExtendedAd
            logger.debug("getAdById - {}", extendedAdDTO);
            return extendedAdDTO;
        } else {
            logger.debug("Ad not found");
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public void removeAd(int id) {
        logger.debug("removeAd with id - {}", id);
        adRepository.deleteById((long) id);
    }

    @Override
    public AdDTO updateAd(int id, CreateOrUpdateAdDTO ad) {
        Optional<AdEntity> adEntityOptional = adRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            adMapper.createOrUpdateAdToAdEntity(ad);
            AdEntity updatedAd = adRepository.save(adEntity);
            AdDTO updateAd = adMapper.adEntityToAdDTO(updatedAd); // Преобразование и возврат обновленного объявления
            logger.debug("updateAd - {}", updateAd);
            return updateAd;
        } else {
            logger.debug("updateAd - Ad not found");
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public void updateAdImage(int id, MultipartFile image) {
        Optional<AdEntity> adEntityOptional = adRepository.findById((long) id);
        if (adEntityOptional.isPresent()) {
            AdEntity adEntity = adEntityOptional.get();
            adEntity.setImage(image.getOriginalFilename());
            logger.debug("updateAdImage - {}", image.getOriginalFilename() );
            adRepository.save(adEntity);
        } else {
            logger.debug("updateAdImage - Ad not found");
            throw new RuntimeException("Ad not found");
        }
    }

    @Override
    public AdsDTO getAdsForLoggedInUser(UserEntity userEntity) {
        AdsDTO adsDTO = new AdsDTO();
        List<AdEntity> adEntity1 = userEntity.getAdEntity();
        List<AdDTO> result = new ArrayList<>(List.of());
        for (AdEntity adEntity : adEntity1) {
            result.add(getAdDTO(adEntity));
        }
        adsDTO.setResults(result);
        adsDTO.setCount(result.size());
        logger.debug("getAdsForLoggedInUser - {}", adsDTO);
        return adsDTO;
    }

    @Override
    public boolean isAuthorAd(String email, Long adId) {
        AdEntity adEntity = adRepository.findById(adId).orElseThrow(RuntimeException::new);
        return adEntity.getEmail().equals(email);
    }

    @Override
    public AdEntity findById(Long id) {
        try {
            return adRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
