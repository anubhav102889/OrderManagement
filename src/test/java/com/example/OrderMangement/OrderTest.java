package com.example.OrderMangement;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.OrderMangement.Models.CouponInformation;
import com.example.OrderMangement.Repositories.OrderDetailsRespoitory;
import com.example.OrderMangement.Services.OrderDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

	@MockBean
	private OrderDetailsRespoitory respoitory;
	
	@Autowired
	private OrderDetailsService service;
	
	@Test
	public void testcoupon() {
		Mockito.when(respoitory.couponInfo(123.0)).thenReturn(Stream.of(new CouponInformation("test",1234.0))
				.collect(Collectors.toList()));
		assertEquals(service.couponInfo(123.0).size(), 1);
	
	}
}
