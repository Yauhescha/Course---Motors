package org.gstu.zagoruev.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.gstu.zagoruev.entity.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandServiceTest {
	@Autowired
	BrandService service;
	Brand read;
	
	String str = "test" + System.currentTimeMillis();
	String strEdit = "testEdit" + System.currentTimeMillis();

	@Test
	public void testService() throws Exception {
		if (service.repository.findByName(str) != null)
			service.delete(service.repository.findByName(str).getId());
		Brand temp = new Brand();
		temp.setName(str);
		service.create(temp);
		read = service.repository.findByName(str);
		assertEquals(str, read.getName());

		read.setName(strEdit);
		service.update(read);
		read = service.read(read.getId());
		assertEquals(read.getName(), strEdit);

		service.delete(read.getId());
		Assertions.assertThrows(Exception.class, () -> {
			service.read(read.getId()).getId();
		});
	}
}
