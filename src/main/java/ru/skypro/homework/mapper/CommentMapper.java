package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.CommentEntity;


@Component
public class CommentMapper {
    public CommentDTO toDto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setAuthorImage(entity.getAuthorImage());
        dto.setAuthorFirstName(entity.getAuthorFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getCommentId());
        dto.setText(entity.getText());
        return dto;
    }

    public CommentEntity toEntity(CreateOrUpdateCommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setAuthor(entity.getAuthor());
        entity.setAuthorImage(entity.getAuthorImage());
        entity.setAuthorFirstName(entity.getAuthorFirstName());
        entity.setCreatedAt(entity.getCreatedAt());
        entity.setCommentId(entity.getCommentId());
        entity.setText(entity.getText());
        return entity;
    }
}
