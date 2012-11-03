package com.piotr.stockexchange.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.piotr.stockexchange.domain.Order;
import com.piotr.stockexchange.domain.Transaction;

@Repository("TransactionDao")
@Transactional
public class TransactionDao {
    
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void processTransactions(List<Transaction> transactions) {
        for(Transaction transaction : transactions) {
            processOrder(transaction, transaction.getAsk());
            processOrder(transaction, transaction.getBid());
            em.persist(transaction);
        }
    }
    
    private void processOrder(Transaction transaction, Order order) {
        order.setAmountProcessed(transaction.getAmount());
        if(order.getAmount() == order.getAmountProcessed()) {
            order.setFinished(true);
        }
    }
    
}
