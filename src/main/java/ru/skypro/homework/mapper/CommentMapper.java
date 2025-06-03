package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;


import java.time.Instant;


@Component
public class CommentMapper {
    public Comment toDto(Comment entity) {
        Comment dto = new Comment();
        dto.setAuthor(entity.getAuthor());
        dto.setAuthorImage(entity.getAuthorImage());
        dto.setAuthorFirstName(entity.getAuthorFirstName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPk(entity.getPk());
        dto.setText(entity.getText());
        return dto;
    }

    public Comment toEntity(CreateOrUpdateComment dto) {
        Comment entity = new Comment();
        entity.setAuthor(entity.getAuthor());
        entity.setAuthorImage(entity.getAuthorImage());
        entity.setAuthorFirstName(entity.getAuthorFirstName());
        entity.setCreatedAt(entity.getCreatedAt());
        entity.setPk(entity.getPk());
        entity.setText(entity.getText());
        return entity;
    }
}
