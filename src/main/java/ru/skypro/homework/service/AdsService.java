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

    /**
     * Вовращает ДТО объявления
     * @param adEntity
     * @return AdDTO
     */
    AdDTO getAdDTO(AdEntity adEntity);

    /**
     * Возвращает объявление
     * @param adDTO
     * @return adEntity
     */
    AdEntity getAd(AdDTO adDTO);

    /**
     * Возвращает все объявления
     * @return adsDTO
     */
    List<AdDTO> getAllAds();

    /**
     * Добавляет объявление
     * @param properties
     * @param image
     * @param userEntity
     * @return adDTO
     */
    AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, UserEntity userEntity);

    /**
     * Добавляет комментарий к объявлению
     * @param id
     * @param comment
     * @return commentEntity
     */
    CommentEntity addCommentToAdd(Integer id, CreateOrUpdateCommentDTO comment);

    /**
     * Возвращает объявление по его id
     * @param id
     * @return extendedAdDTO
     */
    ExtendedAdDTO getAdById(int id);

    /**
     * Удаляет объявление
     * @param id
     */
    void removeAd(int id);

    /**
     * Обновляет объявление
     * @param id
     * @param ad
     * @return adDTO
     */
    AdDTO updateAd(int id, CreateOrUpdateAdDTO ad);

    /**
     * Обновляет изображение объявления
     * @param id
     * @param image
     */
    void updateAdImage(int id, MultipartFile image);

    /**
     * Возвращает объявления авторизованного пользователя
     * @param userEntity
     * @return adsDTO
     */
    AdsDTO getAdsForLoggedInUser(UserEntity userEntity);

    /**
     * Проверяет является ли пользователь автором объявления
     * @param email
     * @param adId
     * @return true or false
     */
    boolean isAuthorAd(String email, Long adId);

    /**
     * Возращает AdEntity по id
     * @param id
     * @return adEntity
     */
    AdEntity findById(Long id);
}
