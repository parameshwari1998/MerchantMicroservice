package com.project.merchantmicroservice.repository;

import com.project.merchantmicroservice.entity.ProductDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails,String> {

    @Query(
            value = "update Cart set quantity=?3 where product_id=?1 and merchant_id=?2",
            nativeQuery = true
    )
    ProductDetails updateProductQuantity(String productId, String merchantId,Integer quantity);

    @Query(
            value = "update Cart set price=?3 where product_id=?1 and merchant_id=?2",
            nativeQuery = true
    )
    ProductDetails updateProductPrice(String productId, String merchantId,Integer price);


    @Query(
            value = "select * from Cart where product_id=?1 and merchant_id=?2",
            nativeQuery = true
    )
    ProductDetails getProductDetails(String productId, String merchantId);


}
