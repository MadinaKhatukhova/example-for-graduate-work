package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads/{id}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable long adId) {
        CommentsDTO comments = commentService.getComments(adId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable long adId,
                                                 @RequestBody CreateOrUpdateCommentDTO comment) {
        CommentDTO addedComment = commentService.addComment(adId, comment);
        return ResponseEntity.ok(addedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long adId,
                                              @PathVariable long commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long adId,
                                                    @PathVariable long commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO comment) {
        CommentDTO updatedComment = commentService.updateComment(adId, commentId, comment);
        return ResponseEntity.ok(updatedComment);
    }
}


