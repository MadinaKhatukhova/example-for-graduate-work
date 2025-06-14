package ru.skypro.homework.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для того чтобы наши мапперы инициализировали DTOшки только
 * соответствующие поля, а те что не идентичны самим нужно заполнить,
 * это для того, чтобы не было не обдуманного присвоение значений полям
 */

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

}