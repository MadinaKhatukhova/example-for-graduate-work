package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CommentsMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final CommentsMapper commentsMapper;

    public CommentService(CommentRepository commentRepository,
                          AdRepository adRepository,
                          UserRepository userRepository,
                          CommentMapper commentMapper,
                          CommentsMapper commentsMapper) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.commentsMapper = commentsMapper;
    }

    public CommentsDTO getComments(Integer adId) {
        List<CommentEntity> commentEntityDTOS = commentRepository.findByAdId(adId);
        return commentsMapper.toDto(commentEntityDTOS);
    }

    public CommentDTO addComment(Integer adId, CreateOrUpdateCommentDTO dto, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        CommentDTO entity = commentMapper.toEntity(dto);
        //entity.setAd(ad);
        entity.setAuthor(user);
        entity.setCreatedAt(Instant.now());

        CommentDTO savedEntity = commentRepository.save(entity);
        return commentMapper.toDto(savedEntity);
    }

    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteByAdIdAndId(adId, commentId);
    }

    public CommentDTO updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO dto) {
        CommentDTO entity = commentRepository.findByAdIdAndId(adId, commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        entity.setText(dto.getText());
        CommentDTO updatedEntity = commentRepository.save(entity);
        return commentMapper.toDto(updatedEntity);
    }
}
