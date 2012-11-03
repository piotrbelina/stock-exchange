package com.piotr.stockexchange.domain;

import java.util.ArrayList;
import java.util.List;

public class Matcher {
    public List<Transaction> match(List<BuyOrder> bids, SellOrder ask) {
        List<Transaction> result = new ArrayList<Transaction>();
        Long amountResting = ask.getAmount();
        for (BuyOrder bid : bids) {
            if (amountResting == 0) {
                break;
            }
            if (match(bid, ask)) {
                Transaction transaction = new Transaction();
                transaction.setBid(bid);
                transaction.setAsk(ask);
                transaction.setPrice(getPrice(bid, ask));
                transaction.setAmount(getAmount(bid, amountResting));
                result.add(transaction);

                amountResting -= getAmount(bid, amountResting);
            }
        }
        return result;

    }

    public List<Transaction> match(List<SellOrder> asks, BuyOrder bid) {
        List<Transaction> result = new ArrayList<Transaction>();
        Long amountResting = bid.getAmount();
        for (SellOrder ask : asks) {
            if (amountResting == 0) {
                break;
            }
            if (match(ask, bid)) {
                Transaction transaction = new Transaction();
                transaction.setBid(bid);
                transaction.setAsk(ask);
                transaction.setPrice(getPrice(ask, bid));
                transaction.setAmount(getAmount(ask, amountResting));
                result.add(transaction);

                amountResting -= getAmount(ask, amountResting);
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

    public Long getAmount(BuyOrder bid, Long amountResting) {
        if (bid.getAmount() >= amountResting) {
            return amountResting;
        }
        return bid.getAmount();
    }

    public long getAmount(SellOrder ask, Long amountResting) {
        if (ask.getAmount() >= amountResting) {
            return amountResting;
        }
        return ask.getAmount();
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

}
