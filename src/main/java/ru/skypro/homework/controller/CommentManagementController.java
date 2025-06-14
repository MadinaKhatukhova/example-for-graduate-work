package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

@RestController
@RequestMapping("/ads/{adId}/comments/{commentId}")
public class CommentManagementController {

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO comment) {
        return ResponseEntity.ok().build();
    }
}
