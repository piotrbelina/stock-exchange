package com.piotr.stockexchange.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.piotr.stockexchange.domain.BuyOrder;
import com.piotr.stockexchange.domain.Market;
import com.piotr.stockexchange.domain.Matcher;
import com.piotr.stockexchange.domain.Product;
import com.piotr.stockexchange.domain.SellOrder;
import com.piotr.stockexchange.domain.Transaction;

@ContextConfiguration(value="Tests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TransactionDao transDao;

    @Test
    @Transactional
    public void testProcessTransactions() {
        Product copper = getProduct("copper");
        
        BuyOrder b1 = getBuyOrder(copper, 10L, 10.0);
        BuyOrder b2 = getBuyOrder(copper, 10L, 10.0);
        
        SellOrder s1 = getSellOrder(copper, 15L, 10.0);
        
        ArrayList<BuyOrder> bids = new ArrayList<BuyOrder>();
        bids.add(b1);
        bids.add(b2);
        
        Matcher matcher = new Matcher();
        List<Transaction> transactions = matcher.match(bids, s1);
        
        transDao.processTransactions(transactions);
        
        assertTrue(b1.isFinished());
        assertFalse(b2.isFinished());
        assertFalse(s1.isFinished());
        
        assertEquals(5L, (long)b2.getAmountProcessed());
    }

    private BuyOrder getBuyOrder(Product product, long amount, double price) {
        BuyOrder order = new BuyOrder();
        order.setAmount(amount);
        order.setPrice(price);
        order.setProduct(product);
        order.setUsername("Piotr");

        return order;
    }

    private SellOrder getSellOrder(Product product, long amount, double price) {
        SellOrder order = new SellOrder();
        order.setAmount(amount);
        order.setPrice(price);
        order.setProduct(product);
        order.setUsername("Piotr");

        return order;
    }

    private Product getProduct(String name) {
        Product product = new Product();
        product.setName(name);
        return product;
    }
}
