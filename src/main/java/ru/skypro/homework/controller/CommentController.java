package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.config.UserPrincipal;
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
                                                 @RequestBody CreateOrUpdateCommentDTO comment,
                                                 @AuthenticationPrincipal UserPrincipal userPrincipal){
        CommentDTO addedComment = commentService.addComment(adId, comment, userPrincipal.getUser());
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long adId,
                                              @PathVariable long commentId,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (commentService.isCommentOwner(commentId, userPrincipal.getUser().getUserId())) {
            commentService.deleteComment(adId, commentId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable long adId,
            @PathVariable long commentId,
            @RequestBody CreateOrUpdateCommentDTO comment,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (commentService.isCommentOwner(commentId, userPrincipal.getUser().getUserId())) {
            CommentDTO updatedComment = commentService.updateComment(adId, commentId, comment);
            return ResponseEntity.ok(updatedComment);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}


