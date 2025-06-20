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
    // Вовращает ДТО объявления AdDTO
    AdDTO getAdDTO(AdEntity adEntity);

    // Возвращает объявление
    AdEntity getAd(AdDTO adDTO);

    // Возвращает все объявления
    List<AdDTO> getAllAds();

    // Добавляет объявление
    AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, UserEntity userEntity);

    // Добавляет комментарий к объявлению
    CommentEntity addCommentToAdd(Integer id, CreateOrUpdateCommentDTO comment);

    // Возвращает объявление по его id
    ExtendedAdDTO getAdById(int id);

    // Удаляет объявление
    void removeAd(int id);

    // Обновляет объявление
    AdDTO updateAd(int id, CreateOrUpdateAdDTO ad);

    // Обновляет изображение объявления
    void updateAdImage(int id, MultipartFile image);

    // Возвращает объявления авторизованного пользователя
    AdsDTO getAdsForLoggedInUser(UserEntity userEntity);

    // Проверяет является ли пользователь автором объявления
    boolean isAuthorAd(String email, Long adId);

    // Возращает AdEntity по id
    AdEntity findById(Long id);
}
