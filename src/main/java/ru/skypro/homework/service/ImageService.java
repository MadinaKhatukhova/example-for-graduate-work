package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.model.UserEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public interface ImageService {
    void uploadImage(Long userId, MultipartFile image)throws IOException;

    ImageEntity getImageById(Long id);

    void save(ImageEntity imageEntity);

}
