package com.project.merchantmicroservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
@Setter
@Getter
@Table(name = ProductDetails.PRODUCTDETAILS_TABLE_NAME)
public class ProductDetails {
    public static final String PRODUCTDETAILS_TABLE_NAME = "PRODUCTDETAILS";
    @Id
    @Column(name = "productdetails_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String productDetailsId;
    private String productId;
//    @ManyToOne
//    @JoinColumn(name = "merchant_id")
    private String merchantId;
    private Integer productQuantity;
    private Integer totalProductsSold;
    private double productPrice;

}