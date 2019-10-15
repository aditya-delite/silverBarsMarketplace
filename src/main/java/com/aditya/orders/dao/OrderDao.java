package com.aditya.orders.dao;

import java.util.List;

import com.aditya.orders.exception.OrderCancellationException;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.exception.OrderSummaryException;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;

/**
 * This is an order dao interface
 * 
 * @author aditya-gu
 *
 */
public interface OrderDao {

	OrderEntity createOrder(OrderEntity order) throws OrderCreationException;

	boolean cancelOrder(OrderEntity order) throws OrderCancellationException;

	List<List<OrderSummaryEntity>> getOrdersSummary() throws OrderSummaryException;

}
