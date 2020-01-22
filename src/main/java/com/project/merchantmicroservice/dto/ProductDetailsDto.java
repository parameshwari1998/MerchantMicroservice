package com.project.merchantmicroservice.dto;

import com.project.merchantmicroservice.entity.Merchant;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDetailsDto {

    private String productId;
    private String merchantId;
    private int productQuantity;
    private long totalProductsSold;
    private double productPrice;

}
