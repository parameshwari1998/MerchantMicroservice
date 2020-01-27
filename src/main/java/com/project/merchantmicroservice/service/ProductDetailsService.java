package com.project.merchantmicroservice.service;

import com.project.merchantmicroservice.dto.*;
import com.project.merchantmicroservice.entity.ProductDetails;

import java.util.List;

public interface ProductDetailsService {

    void updateProductDetails(ProductDetails productDetails);
    void updateProduct(ProductDetails productDetails);
    void removeProduct(String productId,String merchantId);
    ProductDetails getProductDetails(String productId,String merchantId);
    List<ProductDetails> getProductOfMerchant(String merchantId);
    boolean updateSoldProducts(List<SoldProductsDto> soldProductsDtos);
    List<MerchantDetailsForProductPageDto> getMerchantsForProduct(String productId) throws Exception;

    List<ProductDetails> merchantsForProduct(String productId);
    List<ProductDetails> getproductsOrderByProductQuantityDesc(String merchantId);
    List<ProductDetails> getproductsOrderByProductPriceDesc(String merchantId);


}
