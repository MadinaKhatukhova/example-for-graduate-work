package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdsServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdMapper adMapper;

    @Mock
    private UserEntity userEntity;

    @InjectMocks
    private AdsServiceImpl adsService;

    @Test
    void getAllAds_shouldReturnListOfAdDTO() {
        // Arrange
        AdEntity adEntity1 = new AdEntity();
        AdEntity adEntity2 = new AdEntity();
        List<AdEntity> adEntities = List.of(adEntity1, adEntity2);

        AdDTO adDTO1 = new AdDTO();
        AdDTO adDTO2 = new AdDTO();

        when(adRepository.findAll()).thenReturn(adEntities);
        when(adMapper.adEntityToAdDTO(adEntity1)).thenReturn(adDTO1);
        when(adMapper.adEntityToAdDTO(adEntity2)).thenReturn(adDTO2);

        // Act
        List<AdDTO> result = adsService.getAllAds();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(adDTO1, adDTO2)));
        verify(adRepository, times(1)).findAll();
    }

    @Test
    void addAd_shouldSaveAdAndReturnAdDTO() {
        // Arrange
        CreateOrUpdateAdDTO createAdDTO = new CreateOrUpdateAdDTO();
        createAdDTO.setTitle("Test Ad");
        createAdDTO.setPrice(1000);

        MockMultipartFile image = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test image".getBytes());

        AdEntity savedAdEntity = new AdEntity();
        savedAdEntity.setId(1L);
        savedAdEntity.setTitle("Test Ad");
        savedAdEntity.setPrice(1000);
        savedAdEntity.setImage("test.jpg");

        AdDTO expectedAdDTO = new AdDTO();
        expectedAdDTO.setPk(1);
        expectedAdDTO.setTitle("Test Ad");
        expectedAdDTO.setPrice(1000);

        when(adMapper.createOrUpdateAdToAdEntity(createAdDTO)).thenReturn(savedAdEntity);
        when(adRepository.save(savedAdEntity)).thenReturn(savedAdEntity);
        when(adMapper.adEntityToAdDTO(savedAdEntity)).thenReturn(expectedAdDTO);

        // Act
        AdDTO result = adsService.addAd(createAdDTO, image, userEntity);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPk());
        assertEquals("Test Ad", result.getTitle());
        verify(adRepository, times(1)).save(savedAdEntity);
    }

    @Test
    void getAdById_shouldReturnExtendedAdDTO_whenAdExists() {
        // Arrange
        long adId = 1L;
        AdEntity adEntity = new AdEntity();
        adEntity.setId(adId);

        ExtendedAdDTO expectedExtendedAdDTO = new ExtendedAdDTO();
        expectedExtendedAdDTO.setPk((int) adId);

        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));
        when(adMapper.adEntityToExtendedAd(adEntity)).thenReturn(expectedExtendedAdDTO);

        // Act
        ExtendedAdDTO result = adsService.getAdById((int) adId);

        // Assert
        assertNotNull(result);
        assertEquals((int) adId, result.getPk());
        verify(adRepository, times(1)).findById(adId);
    }

    @Test
    void getAdById_shouldThrowException_whenAdNotFound() {
        // Arrange
        long adId = 999L;
        when(adRepository.findById(adId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> adsService.getAdById((int) adId));
        verify(adRepository, times(1)).findById(adId);
    }

    @Test
    void removeAd_shouldDeleteAd_whenAdExists() {
        // Arrange
        long adId = 1L;

        // Act
        adsService.removeAd(Math.toIntExact(adId));

        // Assert
        verify(adRepository, times(1)).deleteById(adId);
    }

    @Test
    void updateAdImage_shouldUpdateImage_whenAdExists() {
        // Arrange
        long adId = 1L;
        MockMultipartFile newImage = new MockMultipartFile(
                "newImage", "new.jpg", "image/jpeg", "new image".getBytes());

        AdEntity existingAd = new AdEntity();
        existingAd.setId((long) adId);
        existingAd.setImage("old.jpg");

        when(adRepository.findById(adId)).thenReturn(Optional.of(existingAd));

        // Act
        adsService.updateAdImage(Math.toIntExact(adId), newImage);

        // Assert
        assertEquals("new.jpg", existingAd.getImage());
        verify(adRepository, times(1)).save(existingAd);
    }

    @Test
    void isAuthorAd_shouldReturnTrue_whenUserIsAuthor() {
        // Arrange
        String userEmail = "user@example.com";
        long adId = 1L;

        AdEntity adEntity = new AdEntity();
        adEntity.setId(adId);
        adEntity.setEmail(userEmail);

        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));

        // Act
        boolean result = adsService.isAuthorAd(userEmail, (long) Math.toIntExact(adId));

        // Assert
        assertTrue(result);
    }

    @Test
    void isAuthorAd_shouldReturnFalse_whenUserIsNotAuthor() {
        // Arrange
        String userEmail = "user@example.com";
        String otherEmail = "other@example.com";
        long adId = 1L;

        AdEntity adEntity = new AdEntity();
        adEntity.setId(adId);
        adEntity.setEmail(otherEmail);

        when(adRepository.findById(adId)).thenReturn(Optional.of(adEntity));

        // Act
        boolean result = adsService.isAuthorAd(userEmail, (long) Math.toIntExact(adId));

        // Assert
        assertFalse(result);
    }

    @Test
    void findById_shouldReturnAdEntity_whenAdExists() {
        // Arrange
        long adId = 1L;
        AdEntity expectedAd = new AdEntity();
        expectedAd.setId(adId);

        when(adRepository.findById(adId)).thenReturn(Optional.of(expectedAd));

        // Act
        AdEntity result = adsService.findById((long) Math.toIntExact(adId));

        // Assert
        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
    }
}