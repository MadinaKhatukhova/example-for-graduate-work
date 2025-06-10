package ru.skypro.homework.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Integer id) {
        super("CommentDTO with id " + id + " not found");
    }
}
