package com.aditya.orders.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aditya.orders.dao.OrderDao;
import com.aditya.orders.exception.OrderCancellationException;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.exception.OrderSummaryException;
import com.aditya.orders.service.OrderService;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;

/**
 * This is the order service implementation class
 * 
 * @author aditya-gu
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public OrderEntity createOrder(OrderEntity order) throws OrderCreationException {
		return orderDao.createOrder(order);
	}

	@Override
	public boolean cancelOrder(OrderEntity order) throws OrderCancellationException {
		return orderDao.cancelOrder(order);
	}

	@Override
	public List<List<OrderSummaryEntity>> getOrdersSummary() throws OrderSummaryException {
		return orderDao.getOrdersSummary();
	}

}
