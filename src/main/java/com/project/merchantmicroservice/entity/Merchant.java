package com.project.merchantmicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Setter
@Getter
@Table(name = Merchant.MERCHANT_TABLE_NAME)
public class Merchant {

    public static final String MERCHANT_TABLE_NAME = "MERCHANT";

    @Id
    private String merchantId;
    private String name;
    private BigInteger contactNo;
    private Integer totalProductsSold;
    private Float merchantRating;
    private String email;

    public Merchant() {
        this.merchantRating = 5f;
    }
}
