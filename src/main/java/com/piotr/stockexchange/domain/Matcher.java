package com.piotr.stockexchange.domain;

import java.util.List;

public class Matcher {
	public MatchResult match(List<BuyOrder> bids, SellOrder ask) {
		MatchResult result = new MatchResult();
		Long amountResting = ask.getAmount();
		for (BuyOrder bid : bids) {
			if (match(bid, ask) && amountResting > 0) {				
				Transaction transaction = new Transaction();
				transaction.setBid(bid);
				transaction.setAsk(ask);
				transaction.setPrice(getPrice(bid, ask));
				transaction.setAmount(getAmount(bid, ask));
				result.add(transaction);
			}
		}
		return result;
		
	}
	
	public double getPrice(BuyOrder bid, SellOrder ask) {
		return bid.getPrice();
	}
	
	public double getPrice(SellOrder ask, BuyOrder bid) {
		return ask.getPrice();
	}	

	public Long getAmount(BuyOrder bid, SellOrder ask) {
		if (bid.getAmount() >= ask.getAmount()) {
			return ask.getAmount();
		}
		return bid.getAmount();
	}
	
	public boolean match(BuyOrder bid, SellOrder ask) {
		if (!productMatch(bid, ask)) {
			return false;
		}
		
		if (bid.getPrice() < ask.getPrice()) {
			return false;
		}
		
		return true;
	}
	

	public boolean match(SellOrder ask, BuyOrder bid) {
		if (!productMatch(bid, ask)) {
			return false;
		}
		
		if (ask.getPrice() > bid.getPrice()) {
			return false;
		}
		
		return true;
	}
	
	protected boolean productMatch(Order order1, Order order2) {
		if (!order1.getProduct().equals(order2.getProduct())) {
			return false;
		}
		return true;
	}

	public long getAmount(SellOrder ask, BuyOrder bid) {
		if (ask.getAmount() >= bid.getAmount()) {
			return bid.getAmount();
		}
		return ask.getAmount();
	}
}
