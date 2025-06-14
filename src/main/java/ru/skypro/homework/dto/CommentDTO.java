package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CommentDTO {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
    private Integer pk;
    private String text;

    public CommentDTO() {

    }
}
