package com.piotr.stockexchange.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MatcherTest {

    @Test
    public void testMatch() {
        Product oil = new Product("oil");

        ArrayList<BuyOrder> existingBuyOrders = new ArrayList<BuyOrder>();
        BuyOrder b1 = new BuyOrder(oil, 10.0, (long) 2, "Piotr");
        BuyOrder b2 = new BuyOrder(oil, 10.0, (long) 2, "Piotr");
        existingBuyOrders.add(b1);
        existingBuyOrders.add(b2);

        SellOrder s1 = new SellOrder(oil, 10.0, (long) 3, "Paweł");

        Matcher matcher = new Matcher();
        List<Transaction> result = matcher.match(existingBuyOrders, s1);

        assertNotNull(result);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAmount());
        assertEquals(1, result.get(1).getAmount());
    }
    
    @Test
    public void testMatchSell() {
        Product oil = new Product("oil");

        ArrayList<SellOrder> existingSellOrders = new ArrayList<SellOrder>();
        SellOrder s1 = new SellOrder(oil, 10.0, (long) 2, "Piotr");
        SellOrder s2 = new SellOrder(oil, 10.0, (long) 2, "Piotr");
        existingSellOrders.add(s1);
        existingSellOrders.add(s2);

        BuyOrder b1 = new BuyOrder(oil, 10.0, (long) 3, "Paweł");

        Matcher matcher = new Matcher();
        List<Transaction> result = matcher.match(existingSellOrders, b1);

        assertNotNull(result);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAmount());
        assertEquals(1, result.get(1).getAmount());
    }

    @Test
    public void testMatchExistingBidWithAsk() {
        Product oil = new Product("oil");

        BuyOrder bid = new BuyOrder(oil, 10.0, (long) 2, "Piotr");
        SellOrder ask = new SellOrder(oil, 10.0, (long) 1, "Paweł");

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

        BuyOrder bid = new BuyOrder(oil, 10.0, (long) 2, "Piotr");
        SellOrder ask = new SellOrder(oil, 10.0, (long) 1, "Paweł");

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
        SellOrder ask = new SellOrder(oil, 10.0, (long) 1, "Paweł");

        Matcher matcher = new Matcher();
        assertEquals((long) 1, (long) matcher.getAmount(bid, 1L));

        ask.setAmount((long) 2);
        assertEquals((long) 2, (long) matcher.getAmount(bid, 2L));

        ask.setAmount((long) 3);
        assertEquals((long) 2, (long) matcher.getAmount(bid, 3L));
    }

    @Test
    public void testGetAmountExistingAskWithBid() {
        Product oil = new Product("oil");

        BuyOrder bid = new BuyOrder(oil, 10.0, 1L, "Piotr");
        SellOrder ask = new SellOrder(oil, 10.0, 2L, "Paweł");

        Matcher matcher = new Matcher();
        assertEquals((long) 1, (long) matcher.getAmount(ask, 1L));

        bid.setAmount((long) 2);
        assertEquals((long) 2, (long) matcher.getAmount(ask, 2L));

        bid.setAmount((long) 3);
        assertEquals((long) 2, (long) matcher.getAmount(ask, 3L));
    }

    @Test
    public void testGetPriceExistingBidWithAsk() {
        Product oil = new Product("oil");

        BuyOrder bid = new BuyOrder(oil, 10.0, 2L, "Piotr");
        SellOrder ask = new SellOrder(oil, 10.0, (long) 1, "Paweł");

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
