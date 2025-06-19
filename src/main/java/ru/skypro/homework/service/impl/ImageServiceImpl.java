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
        UserEntity userEntity = userService.getUserById(userId); //поиск нужного студента

        Path filePath;
        try {
            filePath = Path.of(avatarsDir, userEntity + "." + getExtensions(Objects.requireNonNull(image.getOriginalFilename())));
        } catch (Exception e) {
            throw new RuntimeException("ошибка сохранения фотографии в БД");
        }
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        //работа с потоками
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        ImageEntity newImageEntity;
        newImageEntity = imageRepository.findImageByFilePath(filePath.toString());

        if (newImageEntity == null) {
            newImageEntity = new ImageEntity();
        }
        newImageEntity.setId(userId);
        newImageEntity.setData(image.getBytes());
        newImageEntity.setFilePath(filePath.toString());
        newImageEntity.setMediaType(image.getContentType());
        newImageEntity.setFileSize(image.getSize());

        imageRepository.save(newImageEntity);
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