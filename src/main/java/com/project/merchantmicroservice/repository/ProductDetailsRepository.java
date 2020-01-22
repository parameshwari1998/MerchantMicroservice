package com.project.merchantmicroservice.repository;

import com.project.merchantmicroservice.dto.MerchantDetailsForProductPageDto;
import com.project.merchantmicroservice.dto.ProductDetailsDto;
import com.project.merchantmicroservice.dto.ProductDto;
import com.project.merchantmicroservice.entity.ProductDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails,String> {

    @Modifying
    @Query(
            value = "update Cart set product_quantity=:productQuantity where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void updateProductQuantity(@Param("productId") String productId,@Param("merchantId") String merchantId,@Param("productQuantity") Integer productQuantity);

    @Modifying
    @Query(
            value = "update Cart set product_price=:productPrice where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void updateProductPrice(@Param("productId") String productId,@Param("merchantId") String merchantId,@Param("productPrice") double productPrice);


    @Query(
            value = "select * from ProductDetails where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    ProductDetails getProductDetails(@Param("productId") String productId,@Param("merchantId") String merchantId);
 //   ProductDetails findByProductIdAndMerchantId(String productId,String merchantId);


    @Modifying
    @Query(
            value = "update productDetails set product_quantity=product_quantity - :quantityBrought where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void updateProductsSold(@Param("productId") String productId,@Param("merchantId") String merchantId,@Param("quantityBrought") Integer quantityBrought);


    List<ProductDetails> findByMerchant(String merchantId);


    List<ProductDetails> findByProductId(String productId);

    //List<ProductDetails> findByMerchant_name(String merchantName);
}
