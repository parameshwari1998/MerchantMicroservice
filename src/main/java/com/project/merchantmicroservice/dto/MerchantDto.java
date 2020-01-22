package com.project.merchantmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigInteger;

@Getter
@Setter
public class MerchantDto {

    private String merchantId;
    private String name;
    private BigInteger contactNo;
    private int totalProductsSold;
    private int rating;
    private String email;

}
