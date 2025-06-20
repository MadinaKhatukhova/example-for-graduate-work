package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;

import java.util.List;

public interface AdsService {
    // Вовращает
    AdDTO getAdDTO(AdEntity adEntity);

    AdEntity getAd(AdDTO adDTO);

    List<AdDTO> getAllAds();

    AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, UserEntity userEntity);

    CommentEntity addCommentToAdd(Integer id, CreateOrUpdateCommentDTO comment);

    ExtendedAdDTO getAdById(int id);

    void removeAd(int id);

    AdDTO updateAd(int id, CreateOrUpdateAdDTO ad);

    void updateAdImage(int id, MultipartFile image);

    AdsDTO getAdsForLoggedInUser(UserEntity userEntity);

    boolean isAuthorAd(String email, Long adId);

    AdEntity findById(Long id);
}
