package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;

import ru.skypro.homework.mapper.CommentMapper;

import ru.skypro.homework.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AdsService adsService;

    //Добавляет новый комментарий к объявлению.
    public CommentDTO addComment(Integer adId, CreateOrUpdateCommentDTO comment) {
        AdEntity adEntity = adsService.findById(adId);
        CommentEntity newComment = new CommentEntity();
        newComment.setAd(adEntity);
        newComment.setText(comment.getText());

        CommentEntity savedComment = commentRepository.save(newComment);
        return commentMapper.toDto(savedComment);
    }

    // Возвращает список комментариев к объявлению.
    public CommentsDTO getComments(Integer adId) {
        AdEntity adEntity = adsService.findById(adId);
        List<CommentEntity> allByAdId = adEntity.getCommentEntities();
        List<CommentDTO> result = new ArrayList<>();

        for (CommentEntity commentEntity : allByAdId) {
            result.add(commentMapper.toDto(commentEntity));
        }
        CommentsDTO comments = new CommentsDTO();
        comments.setResults(result);
        comments.setCount(result.size());
        return comments;
    }

    // Удаляет комментарий к объявлению.
    public void deleteComment(Integer adId, Integer commentId) {
        AdEntity adEntity = adsService.findById(adId);
        List<CommentEntity> commentEntities = adEntity.getCommentEntities();
        for (CommentEntity commentEntity : commentEntities) {
            if (commentEntity.getId().equals(commentId)) {
                commentRepository.delete(commentEntity);
                return;
            }
        }
    }

    // Обновляет комментарий у объявления.
    public CommentDTO updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO comment) {
        AdEntity adEntity = adsService.findById(adId);
        List<CommentEntity> commentEntities = adEntity.getCommentEntities();
        for (CommentEntity existingComment : commentEntities) {
            if (existingComment.getId().equals(commentId)) {
                existingComment.setText(comment.getText());
                CommentEntity savedComment = commentRepository.save(existingComment);
                return commentMapper.toDto(savedComment);
            }
            throw new RuntimeException("Комментарий не найден");
        }
        throw new RuntimeException("Объявление не найдено");
    }

}
