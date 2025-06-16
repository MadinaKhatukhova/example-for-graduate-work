package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

@Data
public class CommentsDTO {

    private Integer count;
    private List<CommentDTO> results;

    public CommentsDTO() {

    }
}
