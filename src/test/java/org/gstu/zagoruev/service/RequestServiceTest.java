package org.gstu.zagoruev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gstu.zagoruev.entity.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestServiceTest {
	@Autowired
	RequestService service;
	Request read;
	
	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		if (service.repository.findByProductId(-1l) != null)
			service.delete(service.repository.findByProductId(-1l).getId());
		Request temp = new Request();
		temp.setProductId(-1l);
		temp.setCount(-1);
		temp.setAprove(false);
		service.create(temp);
		read = service.repository.findByProductId(-1l);
		
		assertEquals(false,read.getAprove());
		assertEquals(-1,read.getProductId());
		assertEquals(-1, read.getCount());

		read.setAprove(true);
		
		service.update(read);
		read = service.read(read.getId());
		assertEquals(true, read.getAprove());

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}
