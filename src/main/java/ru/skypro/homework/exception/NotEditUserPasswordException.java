package ru.skypro.homework.exception;

public class NotEditUserPasswordException extends RuntimeException {

    public NotEditUserPasswordException(String message) {
        super(message);
    }
}
