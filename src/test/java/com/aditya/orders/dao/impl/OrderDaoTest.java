package com.aditya.orders.dao.impl;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aditya.orders.dao.OrderDao;
import com.aditya.orders.exception.OrderCancellationException;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.exception.OrderSummaryException;
import com.aditya.orders.service.OrderType;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

	@Autowired
	private OrderDao dao;

	@Before
	public void setup() throws OrderCreationException {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(12.0);
		order.setUserId("user1");
		order.setType(OrderType.SELL);
		dao.createOrder(order);
	}

	@Test
	public void testCreateOrder() throws OrderCreationException {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(10.0);
		order.setUserId("user2");
		order.setType(OrderType.SELL);
		OrderEntity entity = dao.createOrder(order);
		Assert.assertEquals(order, entity);
	}

	@Test
	public void testCancelOrder() throws OrderCancellationException {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(12.0);
		order.setUserId("user1");
		order.setType(OrderType.SELL);
		boolean actual = dao.cancelOrder(order);
		Assert.assertTrue(actual);
	}

	@Test
	public void testOrderSummary() throws OrderSummaryException {
		List<List<OrderSummaryEntity>> ordersList = dao.getOrdersSummary();
		for (List<OrderSummaryEntity> summaryList : ordersList) {
			for (OrderSummaryEntity entity : summaryList) {
				Assert.assertEquals(entity.getType(), OrderType.SELL);
			}
			if (!summaryList.isEmpty())
				Assert.assertThat(summaryList.size(), is(1));
		}
	}
}
