package com.project.merchantmicroservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoldProductsDto {
    private String productId;
    private String merchantId;
    private Integer quantityBrought;
}
