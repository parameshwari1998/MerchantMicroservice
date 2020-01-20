package com.project.merchantmicroservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = ProductDetails.PRODUCTDETAILS_TABLE_NAME)
public class ProductDetails {

    public static final String PRODUCTDETAILS_TABLE_NAME = "PRODUCTDETAILS";

    @Id
    @Column(name = "productdetails_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String productDetailsId;
    private String productId;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    private Integer quantity;
    private Integer totalProductsSold;
    private Integer price;

}
