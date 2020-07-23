package org.gstu.zagoruev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gstu.zagoruev.entity.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProducerServiceTest {
	@Autowired
	ProducerService service;
	Producer read;
	
	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		if (service.repository.findByTitle(str) != null)
			service.delete(service.repository.findByTitle(str).getId());
		Producer temp = new Producer();
		temp.setAddress(str);
		temp.setPhone(str);
		temp.setTitle(str);
		service.create(temp);
		read = service.repository.findByTitle(str);
		
		assertEquals(str,read.getPhone());
		assertEquals(str,read.getAddress());
		assertEquals(str, read.getTitle());

		read.setAddress(strEdit);
		read.setPhone(strEdit);
		
		service.update(read);
		read = service.read(read.getId());
		assertEquals(strEdit,read.getAddress());
		assertEquals(strEdit, read.getPhone());

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}
