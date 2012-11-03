package com.piotr.stockexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bid_id", nullable=false)
    private BuyOrder bid;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="ask_id", nullable=false)
    private SellOrder ask;
    
    @Column(nullable = false)
    private long amount;
    
    @Column(nullable = false)
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
