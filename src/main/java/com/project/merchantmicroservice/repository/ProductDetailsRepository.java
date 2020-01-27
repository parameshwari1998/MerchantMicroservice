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
            value = "update productDetails set product_quantity=:productQuantity , product_price=:productPrice where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void update(@Param("productId") String productId,@Param("merchantId") String merchantId,@Param("productQuantity") Integer productQuantity,@Param("productPrice") double productPrice);



    @Query(
            value = "select * from ProductDetails where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    ProductDetails getProductDetails(@Param("productId") String productId,@Param("merchantId") String merchantId);


    @Modifying
    @Query(
            value = "update productDetails set product_quantity=product_quantity - :quantityBrought , total_products_sold=total_products_sold + :quantityBrought  where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void updateProductsSold(@Param("productId") String productId,@Param("merchantId") String merchantId,@Param("quantityBrought") Integer quantityBrought);


    @Modifying
    @Query(
            value = "delete from productDetails where product_id=:productId and merchant_id=:merchantId",
            nativeQuery = true
    )
    void removeProduct(@Param("productId") String productId,@Param("merchantId") String merchantId);


    List<ProductDetails> findByMerchantId(String merchantId);

    List<ProductDetails> findByMerchantIdOrderByProductQuantityDesc(String merchantId);

    List<ProductDetails> findByMerchantIdOrderByProductPriceDesc(String merchantId);




    @Query(
            value = "select p.merchant_id, p.product_price, m.name, m.merchant_rating from ProductDetails p join merchant m on p.merchant_id=m.merchant_id where product_id=:productId order by m.merchant_rating desc",
            nativeQuery = true
    )
    List<MerchantDetailsForProductPageDto> findMerchantsForProduct(@Param("productId") String productId);

    List<ProductDetails> findByProductId(String productId);



}
