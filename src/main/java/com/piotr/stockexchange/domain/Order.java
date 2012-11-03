package com.piotr.stockexchange.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    protected String username;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    protected Product product;

    @Column(nullable = false)
    protected Long amount;
    
    @Column(nullable = false)
    protected Long amountProcessed = 0L;

    @Column(nullable = false)
    protected Double price;
    
    @Column(nullable = false)
    protected boolean finished = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmountProcessed() {
        return amountProcessed;
    }

    public void setAmountProcessed(Long amountProcessed) {
        this.amountProcessed = amountProcessed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Order(Product product, Double price, Long amount, String username) {
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.username = username;
    }

    public Order() {

    }

}
