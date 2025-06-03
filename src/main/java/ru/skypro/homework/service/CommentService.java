package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
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

    public Comments getComments(Integer adId) {
        List<Comment> comments = commentRepository.findByAdId(adId);
        return commentsMapper.toDto(comments);
    }

    public Comment addComment(Integer adId, CreateOrUpdateComment dto, String username) {
        Ad ad = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        Comment entity = commentMapper.toEntity(dto);
        entity.setAd(ad);
        entity.setAuthor(user);
        entity.setCreatedAt(Instant.now());

        Comment savedEntity = commentRepository.save(entity);
        return commentMapper.toDto(savedEntity);
    }

    public void deleteComment(Integer adId, Integer commentId) {
        commentRepository.deleteByAdIdAndId(adId, commentId);
    }

    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment dto) {
        Comment entity = commentRepository.findByAdIdAndId(adId, commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        entity.setText(dto.getText());
        Comment updatedEntity = commentRepository.save(entity);
        return commentMapper.toDto(updatedEntity);
    }
}
