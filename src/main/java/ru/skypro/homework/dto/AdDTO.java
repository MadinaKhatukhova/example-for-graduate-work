package ru.skypro.homework.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class AdDTO {

    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;
}
