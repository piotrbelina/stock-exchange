package com.piotr.stockexchange.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class MatcherTest {

	@Test
	public void testMatch() {
		Product oil = new Product("oil");
		
		ArrayList<BuyOrder> existingBuyOrders = new ArrayList<BuyOrder>();
		BuyOrder b1 = new BuyOrder(oil, 10.0, (long)2, "Piotr");
		existingBuyOrders.add(b1);
		
		SellOrder s1 = new SellOrder(oil, 10.0, (long)1, "Paweł");
		
		Matcher matcher = new Matcher();
		MatchResult result = matcher.match(existingBuyOrders, s1);
		
		assertNotNull(result);
	}
	
	@Test
	public void testMatchExistingBidWithAsk() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, (long)2, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, (long)1, "Paweł");
		
		Matcher matcher = new Matcher();
		assertTrue(matcher.match(bid, ask));
		
		ask.setPrice(11.0);
		assertFalse(matcher.match(bid, ask));
		
		ask.setPrice(9.0);
		assertTrue(matcher.match(bid, ask));
		
	}
	

	@Test
	public void testMatchExistingAskWithBid() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, (long)2, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, (long)1, "Paweł");
		
		Matcher matcher = new Matcher();
		assertTrue(matcher.match(ask, bid));
		
		bid.setPrice(11.0);
		assertTrue(matcher.match(ask, bid));
		
		bid.setPrice(9.0);
		assertFalse(matcher.match(ask, bid));
		
	}
	
	@Test
	public void testGetAmountExistingBidWithAsk() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, 2L, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, (long)1, "Paweł");
		
		Matcher matcher = new Matcher();
		assertEquals((long)1, (long)matcher.getAmount(bid, ask));
		
		ask.setAmount((long) 2);
		assertEquals((long)2, (long)matcher.getAmount(bid, ask));
		
		ask.setAmount((long) 3);
		assertEquals((long)2, (long)matcher.getAmount(bid, ask));
	}
	

	@Test
	public void testGetAmountExistingAskWithBid() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, 1L, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, 2L, "Paweł");
		
		Matcher matcher = new Matcher();
		assertEquals((long)1, (long)matcher.getAmount(ask, bid));
		
		bid.setAmount((long) 2);
		assertEquals((long)2, (long)matcher.getAmount(ask, bid));
		
		bid.setAmount((long) 3);
		assertEquals((long)2, (long)matcher.getAmount(ask, bid));
	}
	

	@Test
	public void testGetPriceExistingBidWithAsk() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, 2L, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, (long)1, "Paweł");
		
		Matcher matcher = new Matcher();
		assertEquals("", 10.0, matcher.getPrice(bid, ask), 0.0005);
		
		ask.setPrice(5.0);
		assertEquals("", 10.0, matcher.getPrice(bid, ask), 0.0005);
		
		ask.setPrice(15.0);
		assertEquals("", 10.0, matcher.getPrice(bid, ask), 0.0005);
	}
	

	@Test
	public void testGetPriceExistingAskWithBid() {
		Product oil = new Product("oil");
		
		BuyOrder bid = new BuyOrder(oil, 10.0, 1L, "Piotr");
		SellOrder ask = new SellOrder(oil, 10.0, 2L, "Paweł");
		
		Matcher matcher = new Matcher();
		assertEquals("", 10.0, matcher.getPrice(ask, bid), 0.0005);
		
		bid.setPrice(5.0);
		assertEquals("", 10.0, matcher.getPrice(ask, bid), 0.0005);
		
		bid.setPrice(15.0);
		assertEquals("", 10.0, matcher.getPrice(ask, bid), 0.0005);
	}
	
}
