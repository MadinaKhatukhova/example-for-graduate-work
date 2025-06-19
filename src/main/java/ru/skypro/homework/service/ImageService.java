package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;

import java.io.*;

public interface ImageService {
    void uploadImage(Long userId, MultipartFile image)throws IOException;

    ImageEntity getImageById(Long id);

    void save(ImageEntity imageEntity);

}
