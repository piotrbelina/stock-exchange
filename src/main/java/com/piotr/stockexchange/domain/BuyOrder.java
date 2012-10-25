package com.piotr.stockexchange.domain;

import javax.persistence.Entity;

@Entity()
public class BuyOrder extends Order {
	public BuyOrder() {
	}
	
	public BuyOrder(Product product, Double price, Long amount, String username) {
		super(product, price, amount, username);
	}
}
