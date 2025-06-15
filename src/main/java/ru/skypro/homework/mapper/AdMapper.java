package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.AdEntity;

@Service
public class AdMapper {
    // Преобразование Entity → DTO
    public AdDTO toDto(AdEntity entity) {
        AdDTO dto = new AdDTO();
        dto.setPk(entity.getId());  // Особое внимание на pk → id
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    // Преобразование DTO → Entity
    public AdEntity toEntity(AdDTO dto) {
        AdEntity entity = new AdEntity();
        entity.setId(dto.getPk());  // Особое внимание на id → pk
        entity.setImage(dto.getImage());
        entity.setPrice(dto.getPrice());
        entity.setTitle(dto.getTitle());
        return entity;
    }

    public AdEntity createOrUpdateAdToAdEntity(CreateOrUpdateAdDTO dto){
        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public ExtendedAdDTO adEntityToExtendedAd(AdEntity entity){
        ExtendedAdDTO dto = new ExtendedAdDTO();
        dto.setPk(entity.getId());
        dto.setAuthorFirstName(entity.getEmail());
        dto.setAuthorLastName(entity.getEmail());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getTitle());
        dto.setPhone(entity.getTitle());
        dto.setEmail(entity.getEmail());
        return dto;
    }

}
