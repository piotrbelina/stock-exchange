package com.piotr.stockexchange.domain;

import javax.persistence.Entity;

@Entity()
public class SellOrder extends Order {

    public SellOrder(Product product, Double price, Long amount, String username) {
        super(product, price, amount, username);
    }

    public SellOrder() {

    }

}
