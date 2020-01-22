package com.project.merchantmicroservice.service;

import com.project.merchantmicroservice.dto.*;
import com.project.merchantmicroservice.entity.ProductDetails;

import java.util.List;

public interface ProductDetailsService {

    void updateProductDetails(ProductDetails productDetails);
    void updateProductQuantity(String productId,String merchantId,Integer quantity);
    void updateProductPrice(String productId,String merchantId,double price);
    ProductDetails getProductDetails(String productId,String merchantId);
    List<ProductDetails> getProductOfMerchant(String merchantId);
    boolean updateSoldProducts(List<SoldProductsDto> soldProductsDtos);
    List<MerchantDetailsForProductPageDto> getMerchantsForProduct(String productId) throws Exception;

}
