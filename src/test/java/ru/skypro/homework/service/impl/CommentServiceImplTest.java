package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private AdsService adsService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void addComment_shouldSaveAndReturnCommentDTO() {
        // Arrange
        Long adId = 1L;
        CreateOrUpdateCommentDTO createComment = new CreateOrUpdateCommentDTO();
        createComment.setText("Test comment");

        AdEntity adEntity = new AdEntity();
        adEntity.setId(Long.valueOf(adId));

        CommentEntity newComment = new CommentEntity();
        newComment.setText("Test comment");
        newComment.setAdEntity(adEntity);

        CommentEntity savedComment = new CommentEntity();
        savedComment.setCommentId(1L);
        savedComment.setText("Test comment");
        savedComment.setAdEntity(adEntity);

        CommentDTO expectedCommentDTO = new CommentDTO();
        expectedCommentDTO.setPk(1);
        expectedCommentDTO.setText("Test comment");

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(savedComment);
        when(commentMapper.toDto(savedComment)).thenReturn(expectedCommentDTO);

        // Act
        CommentDTO result = commentService.addComment(Math.toIntExact(adId), createComment);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPk());
        assertEquals("Test comment", result.getText());
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void getComments_shouldReturnCommentsDTO() {
        // Arrange
        Long adId = 1L;
        AdEntity adEntity = new AdEntity();
        adEntity.setId(Long.valueOf(adId));

        CommentEntity comment1 = new CommentEntity();
        comment1.setCommentId(1L);
        comment1.setText("Comment 1");
        comment1.setAdEntity(adEntity);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCommentId(2L);
        comment2.setText("Comment 2");
        comment2.setAdEntity(adEntity);

        adEntity.setCommentEntities(List.of(comment1, comment2));

        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setPk(1);
        commentDTO1.setText("Comment 1");

        CommentDTO commentDTO2 = new CommentDTO();
        commentDTO2.setPk(2);
        commentDTO2.setText("Comment 2");

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);
        when(commentMapper.toDto(comment1)).thenReturn(commentDTO1);
        when(commentMapper.toDto(comment2)).thenReturn(commentDTO2);

        // Act
        CommentsDTO result = commentService.getComments(Math.toIntExact(adId));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getCount());
        assertEquals(2, result.getResults().size());
        assertTrue(result.getResults().stream().anyMatch(c -> c.getPk() == 1));
        assertTrue(result.getResults().stream().anyMatch(c -> c.getPk() == 2));
    }

    @Test
    void deleteComment_shouldDeleteCommentWhenFound() {
        // Arrange
        Long adId = 1L;
        Integer commentId = 1;

        AdEntity adEntity = new AdEntity();
        adEntity.setId(Long.valueOf(adId));

        CommentEntity comment = new CommentEntity();
        comment.setCommentId(Long.valueOf(commentId));
        comment.setAdEntity(adEntity);

        adEntity.setCommentEntities(List.of(comment));

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);

        // Act
        commentService.deleteComment(Math.toIntExact(adId), commentId);

        // Assert
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void deleteComment_shouldNotThrowWhenCommentNotFound() {
        // Arrange
        Long adId = 1L;
        Integer commentId = 999;

        AdEntity adEntity = new AdEntity();
        adEntity.setId(Long.valueOf(adId));
        adEntity.setCommentEntities(List.of()); // Пустой список комментариев

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);

        // Act & Assert (не должно быть исключения)
        assertDoesNotThrow(() -> commentService.deleteComment(Math.toIntExact(adId), commentId));
        verify(commentRepository, never()).delete(any());
    }

    @Test
    void updateComment_shouldUpdateAndReturnCommentDTO() {
        // Arrange
        Long adId = 1L;
        Integer commentId = 1;
        CreateOrUpdateCommentDTO updateComment = new CreateOrUpdateCommentDTO();
        updateComment.setText("Updated comment");

        AdEntity adEntity = new AdEntity();
        adEntity.setId(Long.valueOf(adId));

        CommentEntity existingComment = new CommentEntity();
        existingComment.setCommentId(Long.valueOf(commentId));
        existingComment.setText("Original comment");
        existingComment.setAdEntity(adEntity);

        adEntity.setCommentEntities(List.of(existingComment));

        CommentEntity updatedComment = new CommentEntity();
        updatedComment.setCommentId(Long.valueOf(commentId));
        updatedComment.setText("Updated comment");
        updatedComment.setAdEntity(adEntity);

        CommentDTO expectedCommentDTO = new CommentDTO();
        expectedCommentDTO.setPk(commentId);
        expectedCommentDTO.setText("Updated comment");

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);
        when(commentRepository.save(existingComment)).thenReturn(updatedComment);
        when(commentMapper.toDto(updatedComment)).thenReturn(expectedCommentDTO);

        // Act
        CommentDTO result = commentService.updateComment(Math.toIntExact(adId), commentId, updateComment);

        // Assert
        assertNotNull(result);
        assertEquals(commentId, result.getPk());
        assertEquals("Updated comment", result.getText());
        verify(commentRepository, times(1)).save(existingComment);
    }

    @Test
    void updateComment_shouldThrowExceptionWhenCommentNotFound() {
        CommentEntity comment = new CommentEntity();
        // Arrange
        Long adId = 1L;
        Integer commentId = 999;
        CreateOrUpdateCommentDTO updateComment = new CreateOrUpdateCommentDTO();
        updateComment.setText("Updated comment");

        AdEntity adEntity = new AdEntity();
        adEntity.setId(adId);
        adEntity.setCommentEntities(List.of()); // Пустой список комментариев

        when(adsService.findById((long) Math.toIntExact(adId))).thenReturn(adEntity);

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                commentService.updateComment(Math.toIntExact(adId), commentId, updateComment)
        );
        verify(commentRepository, never()).save(any());
    }
}