package com.project.merchantmicroservice.service;

import com.project.merchantmicroservice.entity.ProductDetails;

public interface ProductDetailsService {

    ProductDetails updateProductDetails(ProductDetails productDetails);
    ProductDetails updateProductQuantity(String productId,String merchantId,Integer quantity);
    ProductDetails updateProductPrice(String productId,String merchantId,Integer price);
    ProductDetails getProductDetails(String productId,String merchantId);

}
