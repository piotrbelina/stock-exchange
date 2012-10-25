package com.piotr.stockexchange.domain;

public class Transaction {
	private BuyOrder bid;
	private SellOrder ask;
	private long amount;
	private double price;
	public BuyOrder getBid() {
		return bid;
	}
	public void setBid(BuyOrder bid) {
		this.bid = bid;
	}
	public SellOrder getAsk() {
		return ask;
	}
	public void setAsk(SellOrder ask) {
		this.ask = ask;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long quantity) {
		this.amount = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
