package org.gstu.zagoruev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.OrdersProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderProductServiceTest {
	@Autowired
	OrdersProductService service;
	OrdersProduct read;
	
	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		if (service.repository.findByPrice(-1l) != null)
			service.delete(service.repository.findByPrice(-1l).getId());
		OrdersProduct temp = new OrdersProduct();
		temp.setFilename(str);
		temp.setPrice(-1);
		temp.setProductId(-1l);
		temp.setProductName(str);
		temp.setQuantity(-1);
		service.create(temp);
		read = service.repository.findByPrice(-1);
		
		assertEquals(str,read.getFilename());
		assertEquals(str,read.getProductName());
		assertEquals(-1, read.getPrice());

		read.setFilename(strEdit);
		read.setProductName(strEdit);
		
		service.update(read);
		read = service.read(read.getId());
		assertEquals(strEdit,read.getFilename());
		assertEquals(strEdit,read.getProductName());

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}
