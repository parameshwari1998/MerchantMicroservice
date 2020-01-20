package com.project.merchantmicroservice.service;

import com.project.merchantmicroservice.entity.Merchant;

public interface MerchantService {

    Merchant updateMerchant(Merchant merchant);
    boolean isMerchant(String id);
    Merchant getMerchantDetails(String merchantId);


}
