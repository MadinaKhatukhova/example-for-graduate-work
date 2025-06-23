package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skypro.homework.config.MapperConfig;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.CommentEntity;


@Component
public class CommentMapper {
    private final MapperConfig mapper;

    @Autowired
    public CommentMapper(MapperConfig mapper) {
        this.mapper = mapper;
    }

    public CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity) {
        return mapper.getMapper().map(commentEntity, CommentDTO.class);
    }

//    public CommentEntity commentDTOToCommentEntityWithoutId(CommentDTO commentDTO) {
//        CommentEntity commentEntity = mapper.getMapper().map(commentDTO, CommentEntity.class);
//        commentEntity.setAdEntity(commentEntity.getAdEntity());
//        return commentEntity;
//    }

public CommentEntity createOrUpdateCommentDTOToCommentEntity(CreateOrUpdateCommentDTO dto) {
    return mapper.getMapper().map(dto, CommentEntity.class);
}

}
