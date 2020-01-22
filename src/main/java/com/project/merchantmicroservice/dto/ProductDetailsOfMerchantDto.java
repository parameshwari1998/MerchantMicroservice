package com.project.merchantmicroservice.dto;

import com.project.merchantmicroservice.entity.ProductDetails;
import lombok.Data;

import java.util.Map;

@Data
public class ProductDetailsOfMerchantDto {

    private ProductDetails productDetails;
    private String categoryId;
    private String categoryName;
    private String productName;
    private String imageUrl;
    private Map<String,String> productAttributes;
    private String productDescription;
    private int productRating;
}
