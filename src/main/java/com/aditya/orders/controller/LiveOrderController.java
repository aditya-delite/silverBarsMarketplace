package com.aditya.orders.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.orders.constants.OrderConstants;
import com.aditya.orders.dto.OrderDTO;
import com.aditya.orders.dto.OrderSummaryDTO;
import com.aditya.orders.exception.OrderCancellationException;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.exception.OrderSummaryException;
import com.aditya.orders.service.OrderService;
import com.aditya.orders.service.OrderType;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;

/**
 * This is the rest controller for live order dashboard.
 * 
 * @author aditya-gu
 *
 */
@RestController
public class LiveOrderController {

	private static final String pound = "\u00a3";

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OrderService orderService;

	/**
	 * This method will create an order in the in-memory data structure
	 * 
	 * @param orderDto
	 * @return order dto
	 * @throws OrderCreationException
	 */
	@PostMapping(value = "/order", consumes = { "application/json" })
	public ResponseEntity<OrderDTO> registerOrder(@Valid @RequestBody OrderDTO orderDto) throws OrderCreationException {
		OrderEntity order = convertToEntity(orderDto);
		OrderEntity orderCreated = orderService.createOrder(order);
		OrderDTO dto = convertToDTO(orderCreated);
		return new ResponseEntity<OrderDTO>(dto, HttpStatus.CREATED);

	}

	/**
	 * This method will cancel the order
	 * 
	 * @param orderDto
	 * @return
	 * @throws OrderCancellationException
	 */
	@DeleteMapping(value = "/order", consumes = { "application/json" })
	public ResponseEntity<Void> cancelOrder(@RequestBody OrderDTO orderDto) throws OrderCancellationException {
		OrderEntity order = convertToEntity(orderDto);
		boolean isOrderCancelled = orderService.cancelOrder(order);
		if (isOrderCancelled)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		throw new OrderCancellationException(OrderConstants.CANCEL_ORDER_EXCEPTION,
				OrderConstants.CANCEL_ORDER_EXCEPTION_DETAILS);
	}

	/**
	 * This method will return the live order summary
	 * 
	 * @return OrderSummaryDTO
	 * @throws OrderCancellationException
	 */
	@GetMapping(value = "/summary")
	public ResponseEntity<OrderSummaryDTO> ordersSummary() throws OrderSummaryException {
		List<List<OrderSummaryEntity>> summary = orderService.getOrdersSummary();
		OrderSummaryDTO dto = convertToSummaryDto(summary);
		return new ResponseEntity<OrderSummaryDTO>(dto, HttpStatus.OK);

	}

	/**
	 * This method will convert the OrderSummaryEntity to OrderSummaryDTO object
	 * 
	 * @param summary
	 * @return OrderSummaryDTO
	 */
	private OrderSummaryDTO convertToSummaryDto(List<List<OrderSummaryEntity>> summary) {

		OrderSummaryDTO dto = new OrderSummaryDTO();
		OrderType orderType = null;
		for (List<OrderSummaryEntity> orderSummary : summary) {
			List<String> summaryList = new ArrayList<>();
			for (OrderSummaryEntity entity : orderSummary) {
				summaryList.add(entity.getQuantity() + " kg for " + pound + entity.getPrice());
				if (OrderType.BUY.equals(entity.getType()))
					orderType = OrderType.BUY;
				else
					orderType = OrderType.SELL;
			}
			if (!summaryList.isEmpty()) {
				if (OrderType.BUY.equals(orderType))
					dto.setBuyOrders(summaryList);
				else
					dto.setSellOrders(summaryList);
			}
		}
		return dto;
	}

	/**
	 * This method will convert from orderDto to orderEntity
	 * 
	 * @param orderDto
	 * @return OrderEntity
	 */
	private OrderEntity convertToEntity(OrderDTO orderDto) {
		OrderEntity order = modelMapper.map(orderDto, OrderEntity.class);
		return order;
	}

	/**
	 * This method will convert from orderEntity to orderDto
	 * 
	 * @param OrderEntity
	 * @return OrderDTO
	 */
	private OrderDTO convertToDTO(OrderEntity order) {
		OrderDTO dto = modelMapper.map(order, OrderDTO.class);
		return dto;
	}
}
