package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;

import java.io.*;

public interface ImageService {
    //метод загрузки изображения
    void uploadImage(Long userId, MultipartFile image)throws IOException;

    //метод получения изображенифя по id
    ImageEntity getImageById(Long id);

    //метод сохранения image entity
    void save(ImageEntity imageEntity);
}
