package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public interface CommentService {

    /**
     * Добавляет комментарий
     * @param adId
     * @param comment
     * @param author
     * @return commentDTO
     */
    CommentDTO addComment(long adId, CreateOrUpdateCommentDTO comment, UserEntity author);

    /**
     * Возвращает список комментариев к объявлению
     * @param adId
     * @return commentsDTO
     */
    CommentsDTO getComments(long adId);

    /**
     * Удаляет комментарий к объявлению
     * @param adId
     * @param commentId
     */
    void deleteComment(long adId, long commentId);

    /**
     * Обновляет комментарий у объявления.
     * @param adId
     * @param commentId
     * @param comment
     * @return commentDTO
     */
    CommentDTO updateComment(long adId, long commentId, CreateOrUpdateCommentDTO comment);

    /**
     * Проверяет, является ли пользователь владельцем комментария
     * @param commentId
     * @param userId
     * @return true or false
     */
    boolean isCommentOwner(long commentId, long userId);
}
