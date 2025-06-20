package ru.skypro.homework.config;


import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Конфигурация для того чтобы наши мапперы инициализировали DTOшки только
 * соответствующие поля, а те что не идентичны самим нужно заполнить,
 * это для того, чтобы не было не обдуманного присвоение значений полям
 */

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);

        // Добавляем кастомный конвертер
        modelMapper.addConverter(new AbstractConverter<LocalDateTime, Integer>() {
            @Override
            protected Integer convert(LocalDateTime source) {
                return source != null ?
                        (int) source.toEpochSecond(ZoneOffset.UTC) :
                        0;
            }
        });

        // Настраиваем маппинг UserEntity -> UserDTO
        modelMapper.typeMap(UserEntity.class, UserDTO.class)
                .addMappings(mapper -> {
                    mapper.map(UserEntity::getUserId, UserDTO::setId); // Явно указываем маппинг ID
                    mapper.skip(UserDTO::setImage); // Пропускаем image, т.к. он обрабатывается отдельно
                });

        // Настройка маппинга CommentEntity -> CommentDTO
        modelMapper.typeMap(CommentEntity.class, CommentDTO.class)
                .addMappings(mapper -> {
                    // Преобразуем UserEntity (author) в Integer (authorId)
                    mapper.map(src -> src.getAuthor().getUserId(), CommentDTO::setAuthor);
                    // Маппим commentId в pk
                    mapper.map(CommentEntity::getCommentId, CommentDTO::setPk);

                    mapper.map(src -> src.getCreatedAt() != null ?
                                    (int) src.getCreatedAt().toEpochSecond(ZoneOffset.UTC) : 0,
                            CommentDTO::setCreatedAt);
                });

        modelMapper.validate();

        return modelMapper;
    }

}