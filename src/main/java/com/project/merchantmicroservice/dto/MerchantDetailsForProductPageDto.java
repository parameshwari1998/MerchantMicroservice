package com.project.merchantmicroservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDetailsForProductPageDto {

    private String merchantId;
    private String merchantName;
    private Double productPrice;
    private Float merchantRating;


}
