package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface AdsService {
    public AdDTO getAdDTO(AdEntity adEntity);

    public AdEntity getAd(AdDTO adDTO);

    public List<AdDTO> getAllAds();

    public AdDTO addAd(CreateOrUpdateAdDTO properties, MultipartFile image, Authentication authentication);

    public ExtendedAdDTO getAdById(int id);

    public void removeAd(int id);

    public AdDTO updateAd(int id, CreateOrUpdateAdDTO ad);

    public void updateAdImage(int id, MultipartFile image);

    public List<AdDTO> getAdsForLoggedInUser();

    public boolean isAuthorAd(String email, Integer adId);

    public AdEntity findById(Integer id);
}
