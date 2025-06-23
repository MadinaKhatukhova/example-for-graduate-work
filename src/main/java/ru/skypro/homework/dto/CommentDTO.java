package ru.skypro.homework.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
public class CommentDTO {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
//    private Integer createdAt;
    private String createdAt;
    private Integer pk;
    private String text;

}
