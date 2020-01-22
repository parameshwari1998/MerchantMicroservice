package com.project.merchantmicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderedItemDto {

    private String id;
    private String orderId;
    private String merchantId;
    private String productId;
    private Integer productPrice;
    private Integer productQuantity;
}
