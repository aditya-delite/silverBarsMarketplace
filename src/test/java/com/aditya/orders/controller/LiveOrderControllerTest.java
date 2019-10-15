package com.aditya.orders.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aditya.orders.dto.OrderDTO;
import com.aditya.orders.exception.OrderCreationException;
import com.aditya.orders.service.OrderService;
import com.aditya.orders.service.OrderType;
import com.aditya.orders.service.entity.OrderEntity;
import com.aditya.orders.service.entity.OrderSummaryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LiveOrderController.class)
public class LiveOrderControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private OrderService orderService;

	@Before
	public void setup() throws OrderCreationException {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(12.0);
		order.setUserId("user1");
		order.setType(OrderType.SELL);
		Mockito.when(orderService.createOrder(Mockito.any(OrderEntity.class))).thenReturn(order);
	}

	@Test
	public void testCreateOrderAPI() throws Exception {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(12.0);
		order.setUserId("user1");
		order.setType(OrderType.BUY);
		Mockito.when(orderService.createOrder(Mockito.any(OrderEntity.class))).thenReturn(order);

		mvc.perform(MockMvcRequestBuilders.post("/order")
				.content(asJsonString(new OrderDTO("user1", 12.0, 303, OrderType.BUY)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.userId").exists());
	}

	@Test
	public void testCancelOrderAPI() throws Exception {
		OrderEntity order = new OrderEntity();
		order.setPrice(303);
		order.setQuantity(12.0);
		order.setUserId("user1");
		order.setType(OrderType.BUY);
		Mockito.when(this.orderService.cancelOrder(Mockito.any(OrderEntity.class))).thenReturn(true);
		mvc.perform(MockMvcRequestBuilders.delete("/order")
				.content(asJsonString(new OrderDTO("user1", 12.0, 303, OrderType.BUY)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testOrderSummaryAPI() throws Exception {
		List<List<OrderSummaryEntity>> orderSummary = new ArrayList<>();
		List<OrderSummaryEntity> sellOrderList = new ArrayList<>();
		sellOrderList.add(new OrderSummaryEntity(OrderType.SELL, 12.0, 305));
		orderSummary.add(sellOrderList);
		Mockito.when(this.orderService.getOrdersSummary()).thenReturn(orderSummary);
		mvc.perform(MockMvcRequestBuilders.get("/summary").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")).andExpect(status().isOk()).andExpect(jsonPath("$.sellOrders").exists());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
