package com.piotr.stockexchange.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import com.piotr.stockexchange.domain.Order;
import com.piotr.stockexchange.domain.Product;
import com.piotr.stockexchange.domain.SellOrder;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderDaoTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private OrderDao dao;

	@Test
	@Transactional
	public void testAddTransaction() {
		Product product = new Product();
		product.setName("Oil");
		
		BuyOrder transaction = new BuyOrder();
		transaction.setAmount((long) 10);
		transaction.setPrice(10.0);
		transaction.setProduct(product);
		transaction.setUsername("Piotr");
		
		SellOrder SellOrder = new SellOrder();
		SellOrder.setAmount((long) 10);
		SellOrder.setPrice(10.0);
		SellOrder.setProduct(product);
		SellOrder.setUsername("Piotr");
		
		assertNull(transaction.getId());
		dao.addOrder(transaction);
		entityManager.flush();
		assertNotNull(transaction.getId());
		
		assertNull(SellOrder.getId());
		dao.addOrder(SellOrder);
		entityManager.flush();
		assertNotNull(SellOrder.getId());
	}

	@Test
	@Transactional
	public void testGetTransactionById() {
		Product product = new Product();
		product.setName("Oil");
		
		BuyOrder transaction = new BuyOrder();
		transaction.setAmount((long) 10);
		transaction.setPrice(10.0);
		transaction.setProduct(product);
		transaction.setUsername("Piotr");

		SellOrder SellOrder = new SellOrder();
		SellOrder.setAmount((long) 10);
		SellOrder.setPrice(10.0);
		SellOrder.setProduct(product);
		SellOrder.setUsername("Piotr");
		
		dao.addOrder(transaction);
		dao.addOrder(SellOrder);
		
		BuyOrder transaction2 = dao.getBuyOrderById(transaction.getId());
		SellOrder SellOrder2 = dao.getSellOrderById(SellOrder.getId());
		
		assertEquals(transaction, transaction2);
		assertEquals(SellOrder, SellOrder2);
	}
	
	@Test
	@Transactional
	public void testGetTransactionsByProduct() {
		Product oil  = getProduct("oil");		
		Product coal = getProduct("coal");
		
		BuyOrder transaction = getBuyOrder(oil, 10, 10.0);
		BuyOrder transaction2 = getBuyOrder(oil, 100, 10.0);
		BuyOrder transaction3 = getBuyOrder(coal, 10, 10.0);
		
		dao.addOrder(transaction);
		dao.addOrder(transaction2);
		dao.addOrder(transaction3);
		
		List<Order> result = dao.getBuyOrdersByProduct(coal);
		assertTrue(result.contains(transaction3));
		assertEquals(1, result.size());
		
		result = dao.getBuyOrdersByProduct(oil);
		
		assertTrue(result.contains(transaction));
		assertTrue(result.contains(transaction2));
		assertEquals(2, result.size());
	}

	
	@Test
	@Transactional
	public void testGetSellTransactionsInOrder() {
		String name = "copper-unique-q123asa";
		Product copper = getProduct(name);
		
		SellOrder s1 = getSellOrder(copper, 10, 20.0);
		SellOrder s2 = getSellOrder(copper, 10, 10.0);
		SellOrder s3 = getSellOrder(copper, 10, 30.0);
		
		dao.addOrder(s1);
		dao.addOrder(s2);
		dao.addOrder(s3);
		
		List<Order> result = dao.getSellOrdersByProduct(copper);
		assertEquals(s2, result.get(0));
		assertEquals(s1, result.get(1));
		assertEquals(s3, result.get(2));
	}
	

	@Test
	@Transactional
	public void testGetBuyTransactionsInOrder() {
		String name = "copper-unique-q12azxcasa";
		Product copper = getProduct(name);
		
		BuyOrder s1 = getBuyOrder(copper, 10, 20.0);
		BuyOrder s2 = getBuyOrder(copper, 10, 10.0);
		BuyOrder s3 = getBuyOrder(copper, 10, 30.0);
		
		dao.addOrder(s1);
		dao.addOrder(s2);
		dao.addOrder(s3);
		
		List<Order> result = dao.getBuyOrdersByProduct(copper);
		assertEquals(s3, result.get(0));
		assertEquals(s1, result.get(1));
		assertEquals(s2, result.get(2));
	}
	
	@Test
	@Transactional
	public void testMatchOrders() {
		// TODO
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
