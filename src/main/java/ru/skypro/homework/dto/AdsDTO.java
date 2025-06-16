package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

@Data
public class AdsDTO {

    private Integer count;

    private List<AdDTO> results;

}
