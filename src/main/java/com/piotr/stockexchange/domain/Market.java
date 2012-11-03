package com.piotr.stockexchange.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.piotr.stockexchange.persistence.OrderDao;
import com.piotr.stockexchange.persistence.TransactionDao;


public class Market {
    @Autowired
    private Matcher matcher;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private TransactionDao transactionDao;
    
    public void newSellOrder(SellOrder ask) {
        List<BuyOrder> bids = orderDao.getBuyOrdersByProduct(ask.getProduct());
        List<Transaction> transactions = matcher.match(bids, ask);
        
        transactionDao.processTransactions(transactions);
    }
    
}
