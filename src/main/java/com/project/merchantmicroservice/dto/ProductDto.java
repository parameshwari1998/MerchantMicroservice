package com.project.merchantmicroservice.dto;


import lombok.Data;

@Data
public class ProductDto {

    private String productId;
    private String productName;
    private String imageUrl;
    private double productPrice;
    private int productQuantity;
    private int totalProductsSold;


}
