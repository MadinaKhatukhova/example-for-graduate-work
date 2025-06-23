package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.AdEntity;

@Service
public class AdMapper {
    @Value("${download.url}")
    protected String downloadUrl;

    private final MapperConfig mapper;

    @Autowired
    public AdMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public AdDTO adEntityToAdDTO(AdEntity adEntity) {
        AdDTO adDTO = mapper.getMapper().map(adEntity, AdDTO.class);
        adDTO.setImage(adEntity.getImageEntity() == null ? null : downloadUrl + adEntity.getImageEntity().getImageId());

        adDTO.setAuthor(adEntity.getUserEntity().getUserId().intValue());
        adDTO.setPk(adEntity.getId().intValue());
        return adDTO;
    }

    public AdEntity adDTOToAdEntityWithoutId(AdDTO adDTO) {
        AdEntity adEntity = mapper.getMapper().map(adDTO, AdEntity.class);
        adEntity.setId(null); // Убедитесь, что ID не установлен при создании новой сущности
        return adEntity;
    }

    public AdEntity createOrUpdateAdToAdEntity(CreateOrUpdateAdDTO createOrUpdateAd) {
        return mapper.getMapper().map(createOrUpdateAd, AdEntity.class);
    }

    public void updateAdEntityFromDto(CreateOrUpdateAdDTO createOrUpdateAd, AdEntity adEntity) {
        adEntity.setPrice(createOrUpdateAd.getPrice());
        adEntity.setTitle(createOrUpdateAd.getTitle());
    }

    public ExtendedAdDTO adEntityToExtendedAd(AdEntity adEntity) {
        ExtendedAdDTO extendedAd = new ExtendedAdDTO();
        extendedAd.setPk(adEntity.getId().intValue()); // Приводим Long к Integer
        extendedAd.setImage(adEntity.getImageEntity() == null ? "" : downloadUrl + adEntity.getImageEntity().getImageId());
        extendedAd.setDescription(adEntity.getDescription());
        extendedAd.setAuthorFirstName(adEntity.getUserEntity().getFirstName());
        extendedAd.setAuthorLastName(adEntity.getUserEntity().getLastName());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());
        return extendedAd;
    }

}
