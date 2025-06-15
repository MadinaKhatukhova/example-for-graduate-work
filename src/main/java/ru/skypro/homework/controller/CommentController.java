package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;

@RestController
@RequestMapping("/ads/{id}/comments")
public class CommentController {

    @GetMapping
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateCommentDTO comment) {
        return ResponseEntity.ok().build();
    }
}
