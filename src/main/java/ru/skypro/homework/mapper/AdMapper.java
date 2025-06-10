package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.model.AdEntity;

@Service
public class AdMapper {
    // Преобразование Entity → DTO
    public AdDTO toDto(AdEntity entity) {
        AdDTO dto = new AdDTO();
        dto.setPk(entity.getPk());  // Особое внимание на pk → id
        dto.setAuthor(entity.getAuthor());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    // Преобразование DTO → Entity
    public AdEntity toEntity(AdDTO dto) {
        AdEntity entity = new AdEntity();
        entity.setPk(dto.getPk());  // Особое внимание на id → pk
        entity.setAuthor(dto.getAuthor());
        entity.setImage(dto.getImage());
        entity.setPrice(dto.getPrice());
        entity.setTitle(dto.getTitle());
        return entity;
    }

}
