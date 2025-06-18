package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;

import java.util.ArrayList;
import java.util.List;

public interface CommentService {
    CommentDTO addComment(long adId, CreateOrUpdateCommentDTO comment);

    // Возвращает список комментариев к объявлению.
    CommentsDTO getComments(long adId);

    // Удаляет комментарий к объявлению.
    void deleteComment(long adId, long commentId);

    // Обновляет комментарий у объявления.
    CommentDTO updateComment(long adId, long commentId, CreateOrUpdateCommentDTO comment);

}
