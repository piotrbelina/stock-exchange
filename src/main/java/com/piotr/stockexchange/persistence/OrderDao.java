package com.piotr.stockexchange.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.piotr.stockexchange.domain.BuyOrder;
import com.piotr.stockexchange.domain.Product;
import com.piotr.stockexchange.domain.SellOrder;
import com.piotr.stockexchange.domain.Order;

@Repository("OrderDao")
@Transactional
public class OrderDao {
	@PersistenceContext
	private EntityManager em;
		
	public void addOrder(Order Order) {
		em.persist(Order);
	}
	
	public BuyOrder getBuyOrderById(long id) {
		return em.find(BuyOrder.class, id);
	}
	
	public SellOrder getSellOrderById(long id) {
		return em.find(SellOrder.class, id);
	}
	
	public List<Order> getSellOrdersByProduct(Product product) {
		String query = "select o from SellOrder o where o.product = :product order by o.price asc";
		return getOrdersQueryByProduct(product, query)
				.getResultList();
	}
	

	public List<Order> getBuyOrdersByProduct(Product product) {
		String query = "select o from BuyOrder o where o.product = :product order by o.price desc";
		return getOrdersQueryByProduct(product, query)
				.getResultList();
	}
	
	
	
	protected TypedQuery<Order> getOrdersQueryByProduct(Product product, String query) { 
		return em.createQuery(query, Order.class)
			.setParameter("product", product);
	}
	
}
