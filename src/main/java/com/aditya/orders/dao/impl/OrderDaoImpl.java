package com.aditya.orders.dao.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.aditya.orders.constants.OrderConstants;
import com.aditya.orders.dao.OrderDao;
import com.aditya.orders.exception.OrderCancellationException;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.exception.OrderSummaryException;
import com.aditya.orders.service.OrderType;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;

/**
 * This class is order dao implmentation class
 * 
 * @author aditya-gu
 *
 */
@Repository
public class OrderDaoImpl implements OrderDao {

	/*
	 * this will act as a database to store all the buy orders in the map as userId
	 * as key and list of user orders as value
	 */
	private static Map<String, List<OrderEntity>> buyOrdersDatabase = new HashMap<>();

	/*
	 * this will act as a database to store all the sell orders in the map as userId
	 * as key and list of user orders as value
	 */
	private static Map<String, List<OrderEntity>> sellOrdersDatabase = new HashMap<>();

	static {
		// to do ...create dummy data for replicating database
	}

	/**
	 * This method will create an order by adding an entry in the in memory data
	 * structure based on orderTye(buy/sell)
	 * 
	 * @throws OrderCreationException
	 */
	@Override
	public OrderEntity createOrder(OrderEntity order) throws OrderCreationException {
		try {
			OrderType type = order.getType();
			if (OrderType.BUY.equals(type))
				return addOrder(buyOrdersDatabase, order);
			else
				return addOrder(sellOrdersDatabase, order);
		} catch (Exception ex) {
			throw new OrderCreationException(OrderConstants.CREATE_ORDER_EXCEPTION,
					OrderConstants.CREATE_ORDER_EXCEPTION_DETAILS);
		}

	}

	/**
	 * This is a helper method for creating an order.
	 */
	private OrderEntity addOrder(Map<String, List<OrderEntity>> database, OrderEntity order) {
		// if userid already exists then update the user entry in the orders db
		if (database.containsKey(order.getUserId())) {
			List<OrderEntity> userOrders = database.get(order.getUserId());
			userOrders.add(order);
			database.put(order.getUserId(), userOrders);
			return order;
		}
		List<OrderEntity> orders = new ArrayList<>();
		orders.add(order);
		database.put(order.getUserId(), orders);
		return order;
	}

	/**
	 * This method will delete the order from the order database
	 * 
	 * @throws OrderCancellationException
	 */
	@Override
	public boolean cancelOrder(OrderEntity order) throws OrderCancellationException {
		String userId = order.getUserId();
		OrderType type = order.getType();
		if (OrderType.BUY.equals(type))
			return deleteOrder(order, userId, buyOrdersDatabase);
		else
			return deleteOrder(order, userId, sellOrdersDatabase);
	}

	/**
	 * This is a helper method to delete the order from the order database
	 * 
	 * @param order
	 * @param userId
	 * @param database
	 * @return
	 * @throws OrderCancellationException
	 */
	private boolean deleteOrder(OrderEntity order, String userId, Map<String, List<OrderEntity>> database)
			throws OrderCancellationException {
		if (!database.containsKey(userId)) {
			throw new OrderCancellationException(OrderConstants.NO_USERID_EXISTS,
					OrderConstants.CANCEL_ORDER_EXCEPTION_DETAILS);
		}
		try {
			List<OrderEntity> userOrders = database.get(userId);
			return userOrders.removeIf(userOrder -> userOrder.getQuantity() == order.getQuantity()
					&& userOrder.getPrice() == order.getPrice());
		} catch (Exception ex) {
			throw new OrderCancellationException(OrderConstants.CANCEL_ORDER_EXCEPTION,
					OrderConstants.CANCEL_ORDER_EXCEPTION_DETAILS);
		}
	}

	/**
	 * This method will return the orders summary
	 * 
	 * @throws OrderCancellationException
	 */
	@Override
	public List<List<OrderSummaryEntity>> getOrdersSummary() throws OrderSummaryException {
		try {
			List<OrderEntity> sellOrders = sellOrdersDatabase.values().stream().flatMap(orderList -> orderList.stream())
					.collect(Collectors.toList());

			List<OrderEntity> buyOrders = buyOrdersDatabase.values().stream().flatMap(orderList -> orderList.stream())
					.collect(Collectors.toList());
			Map<Integer, OrderSummaryEntity> sellOrderMap = createOrdersSummary(sellOrders);
			Map<Integer, OrderSummaryEntity> buyOrderMap = createOrdersSummary(buyOrders);
			List<OrderSummaryEntity> sellSummaryOrders = sortOrders(sellOrderMap, OrderType.SELL);
			List<OrderSummaryEntity> buySummaryOrders = sortOrders(buyOrderMap, OrderType.BUY);
			List<List<OrderSummaryEntity>> resultList = new ArrayList<>();
			resultList.add(sellSummaryOrders);
			resultList.add(buySummaryOrders);
			return resultList;
		} catch (Exception ex) {
			throw new OrderSummaryException(OrderConstants.ORDER_SUMMARY_EXCEPTION,
					OrderConstants.ORDER_SUMMARY_EXCEPTION_DETAILS);
		}

	}

	/**
	 * This method will sort the orders based on the price in ascending/descending
	 * order
	 * 
	 * @param orderMap
	 * @param type
	 * @return
	 */
	private List<OrderSummaryEntity> sortOrders(Map<Integer, OrderSummaryEntity> orderMap, OrderType type) {
		List<OrderSummaryEntity> summaryOrders = orderMap.values().stream().collect(Collectors.toList());
		// sort in ascending order if order type is sell else sort in descending order
		List<OrderSummaryEntity> sortedList = summaryOrders.stream()
				.sorted(type.equals(OrderType.SELL) ? Comparator.comparingInt(OrderSummaryEntity::getPrice)
						: Comparator.comparingInt(OrderSummaryEntity::getPrice).reversed())
				.collect(Collectors.toList());
		return sortedList;
	}

	/**
	 * This method will give a map of price and ordersummary
	 * 
	 * @param orders
	 * @return
	 */
	private Map<Integer, OrderSummaryEntity> createOrdersSummary(List<OrderEntity> orders) {
		// map of price and ordersummaryentity
		Map<Integer, OrderSummaryEntity> orderMap = new HashMap<>();
		for (OrderEntity entity : orders) {
			OrderSummaryEntity summary = new OrderSummaryEntity();
			// if map contains price then update the map value else create a new entry in
			// map
			if (orderMap.containsKey(entity.getPrice())) {
				summary.setPrice(entity.getPrice());
				summary.setQuantity(entity.getQuantity() + orderMap.get(entity.getPrice()).getQuantity());
				summary.setType(entity.getType());
				orderMap.put(entity.getPrice(), summary);
			} else {
				summary.setPrice(entity.getPrice());
				summary.setQuantity(entity.getQuantity());
				summary.setType(entity.getType());
				orderMap.put(entity.getPrice(), summary);
			}
		}
		return orderMap;
	}
}
