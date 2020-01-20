package com.project.merchantmicroservice.dto;

import com.project.merchantmicroservice.entity.Merchant;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDetailsDto {

    private String productId;
    private Merchant merchantId;
    private Integer quantity;
    private Integer totalProductsSold;
    private Integer price;

}
