package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentsMapper {
    private final CommentMapper commentMapper;

    public CommentsMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public CommentsDTO toDto(List<Comment> entities) {
        CommentsDTO dto = new CommentsDTO();
        dto.setCount(entities.size());
        dto.setResults(entities.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
