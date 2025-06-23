package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;

public interface AuthService {

    /**
     * Возвращает результат аутентификации пользователя
     * @param userName
     * @param password
     * @return true or false
     */
    boolean login(String userName, String password);

    /**
     * Возвращает результат регистрации пользователя
     * @param register
     * @return true or false
     */
    boolean register(Register register);
}
