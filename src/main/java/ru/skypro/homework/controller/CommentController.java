package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@RestController
@RequestMapping("/ads/{id}/comments")
public class CommentController {

    @GetMapping
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable Integer id,
                                              @RequestBody CreateOrUpdateComment comment) {
        return ResponseEntity.ok().build();
    }
}
