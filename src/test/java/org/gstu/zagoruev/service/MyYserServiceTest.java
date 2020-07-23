package org.gstu.zagoruev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gstu.zagoruev.entity.MyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyYserServiceTest {
	@Autowired
	MyUserService service;
	MyUser read;
	
	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		if (service.repository.findByUsername(str) != null)
			service.delete(service.repository.findByUsername(str).getId());
		MyUser temp = new MyUser();
		temp.setFio(str);
		temp.setAddress(str);
		temp.setPhone(str);
		temp.setUsername(str);
		temp.setPassword(str);
		
		service.create(temp);
		read = service.repository.findByUsername(str);
		
		assertEquals(str, read.getAddress());
		assertEquals(str, read.getFio());
		assertEquals(str, read.getUsername());
		assertEquals(str, read.getPhone());

		read.setAddress(strEdit);
		read.setFio(strEdit);
		read.setPassword(strEdit);
		read.setPhone(strEdit);
		
		service.update(read);
		read = service.read(read.getId());
		assertEquals(strEdit, read.getAddress());
		assertEquals(strEdit, read.getFio());
		assertEquals(strEdit, read.getPhone());

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}
