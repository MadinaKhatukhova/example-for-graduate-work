package ru.skypro.homework.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("CommentDTO with id " + id + " not found");
    }
}
