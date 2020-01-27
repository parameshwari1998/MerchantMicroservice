package com.project.merchantmicroservice.dto;

import lombok.Data;


@Data
public class RatingDto {

    private String merchantId;
    private int total;
    private Float merchantRating;

}
