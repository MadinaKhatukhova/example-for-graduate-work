package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertisment")
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long author;    //author id

    private String image;

    private Long pk;

    private Integer price;

    private String title;

}
