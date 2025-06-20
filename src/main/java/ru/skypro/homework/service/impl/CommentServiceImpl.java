package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;

import ru.skypro.homework.mapper.CommentMapper;

import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdsService adsService;
    private final UserService userService;

    @Override
    public CommentDTO addComment(long adId, CreateOrUpdateCommentDTO comment, UserEntity author) {
        AdEntity adEntity = adsService.findById(adId);
        if (adEntity == null) {
            throw new RuntimeException("Ad not found with id: " + adId);
        }

        CommentEntity newComment = new CommentEntity();
        newComment.setAdEntity(adEntity);
        newComment.setUserEntity(author);
        newComment.setAuthor(author); // дублирование, нужно выбрать одно поле
        newComment.setText(comment.getText());
        newComment.setCreatedAt((int) (System.currentTimeMillis() / 1000L));
        newComment.setAuthorFirstName(author.getFirstName());
        newComment.setAuthorImage(author.getImageEntity() != null ? author.getImageEntity().getMediaType() : "");

        CommentEntity savedComment = commentRepository.save(newComment);
        return commentMapper.commentEntityToCommentDTO(savedComment);
    }

    @Override
    public CommentsDTO getComments(long adId) {
        AdEntity adEntity = adsService.findById(adId);
        if (adEntity == null) {
            throw new RuntimeException("Ad not found with id: " + adId);
        }

        List<CommentEntity> comments = commentRepository.findByAdEntity(adEntity);
        List<CommentDTO> result = comments.stream()
                .map(commentMapper::commentEntityToCommentDTO)
                .collect(Collectors.toList());

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setResults(result);
        commentsDTO.setCount(result.size());
        return commentsDTO;
    }

    @Override
    public void deleteComment(long adId, long commentId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAdEntity().getId().equals(adId)) {
            throw new RuntimeException("Comment does not belong to ad");
        }

        commentRepository.delete(comment);
    }

    @Override
    public boolean isCommentOwner(long commentId, long userId) {
        return commentRepository.existsByCommentIdAndUserEntityUserId(commentId, userId);
    }

    @Override
    public CommentDTO updateComment(long adId, long commentId, CreateOrUpdateCommentDTO comment) {
        CommentEntity existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!existingComment.getAdEntity().getId().equals(adId)) {
            throw new RuntimeException("Comment does not belong to ad");
        }

        existingComment.setText(comment.getText());
        CommentEntity updatedComment = commentRepository.save(existingComment);
        return commentMapper.commentEntityToCommentDTO(updatedComment);
    }

}
