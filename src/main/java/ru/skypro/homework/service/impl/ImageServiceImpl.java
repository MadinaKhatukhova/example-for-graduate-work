package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    @Value("${avatar.image.directory}")
    private final String avatarsDir;

    private final UserService userService;

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(
            UserService userService,
            ImageRepository imageRepository,
            @Value("${avatar.image.directory}") String avatarsDir) {
        this.userService = userService;
        this.imageRepository = imageRepository;
        this.avatarsDir = avatarsDir;
    }

    //Загружает изображение для пользователя по его ID.
    @Override
    public void uploadImage(Long userId, MultipartFile image) throws IOException {
        UserEntity userEntity = userService.getUserById(userId);

        // Проверка файла
        if (image.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!image.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only images are allowed");
        }

        // Создание/обновление ImageEntity
        ImageEntity imageEntity = userEntity.getImageEntity();
        if (imageEntity == null) {
            imageEntity = new ImageEntity();
        }

        imageEntity.setData(image.getBytes());
        imageEntity.setMediaType(image.getContentType());
        imageEntity.setFileSize(image.getSize());
        imageEntity.setFilePath(generateFilePath(userEntity, image));

        imageRepository.save(imageEntity);

        // Обновляем ссылку у пользователя
        userEntity.setImageEntity(imageEntity);
        userService.saveUser(userEntity);
    }

    private String generateFilePath(UserEntity user, MultipartFile image) {
        String extension = getExtensions(image.getOriginalFilename());
        return Path.of(avatarsDir, user.getEmail() + "." + extension).toString();
    }

    //Извлекает расширение файла из его имени.
    private String getExtensions(String fileName) {
        String extentions = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.debug("extentions is: {}", extentions);
        return extentions;
    }

    @Override
    public ImageEntity getImageById(Long id) {
        log.info("Получение изображения по идентификатору: {}", id);
        return imageRepository.findById(id).orElseThrow(
                () -> new RuntimeException());
    }

    @Override
    public void save(ImageEntity imageEntity) {
        imageRepository.save(imageEntity);
    }

}