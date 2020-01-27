package com.project.merchantmicroservice.repository;

import com.project.merchantmicroservice.entity.Merchant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant,String> {

    @Modifying
    @Query(
            value = "update merchant set merchant_rating=:merchantRating where merchant_id=:merchantId",
            nativeQuery = true
    )
    void updateMerchantRating(@Param("merchantId") String merchantId, @Param("merchantRating") float merchantRating);


}
