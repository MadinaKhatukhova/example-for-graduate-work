package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;


@Component
public class CommentMapper {
    public CommentDTO toDto(CommentDTO entity) {
        CommentDTO dto = new CommentDTO();
        dto.setAuthor(entity.getAuthor());
        dto.setAuthorImage(entity.getAuthorImage());
        dto.setAuthorFirstName(entity.getAuthorFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getPk());
        dto.setText(entity.getText());
        return dto;
    }

    public CommentDTO toEntity(CreateOrUpdateCommentDTO dto) {
        CommentDTO entity = new CommentDTO();
        entity.setAuthor(entity.getAuthor());
        entity.setAuthorImage(entity.getAuthorImage());
        entity.setAuthorFirstName(entity.getAuthorFirstName());
        entity.setCreatedAt(entity.getCreatedAt());
        entity.setPk(entity.getPk());
        entity.setText(entity.getText());
        return entity;
    }
}
