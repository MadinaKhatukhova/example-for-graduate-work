package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
//    List<CommentEntity> findByAdId(Integer adId);
//
//    void deleteByAdIdAndId(Integer adId, Integer commentId);
//
//    CommentEntity findByAdIdAndId(Integer adId, Integer commentId);
}
