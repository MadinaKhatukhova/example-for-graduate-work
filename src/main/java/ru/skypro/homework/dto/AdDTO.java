package ru.skypro.homework.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class AdDTO {

    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
