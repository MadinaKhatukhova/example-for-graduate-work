package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Comments {

    private Integer count;
    private List<Comment> results;

}
