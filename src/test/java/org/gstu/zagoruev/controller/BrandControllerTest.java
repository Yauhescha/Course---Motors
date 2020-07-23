package org.gstu.zagoruev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gstu.zagoruev.MyModel;
import org.gstu.zagoruev.MyRedirectAttributes;
import org.gstu.zagoruev.entity.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BrandControllerTest {
	@Autowired
	private BrandController controller;

	String name="nameForTest";
	String nameEdit="nameForTestEdit";
	Brand read;
	@Test
	public void contexLoads() throws Exception {
		assertNotNull(controller);
	}
	
	@Test
	public void create() {
		if(controller.service.repository.findByName(name)!=null) return;
		controller.setBrand(new MyModel(), name, new MyRedirectAttributes());
		read = controller.service.repository.findByName(name);
		assertEquals(name, read.getName());
	}
	@Test
	public void update() {
		if(read==null)return;
		read = controller.service.repository.findByName(name);
		controller.editBrand(nameEdit, read.getId().intValue(), new MyRedirectAttributes());
		assertEquals(nameEdit, read.getName());
		
		
	}
	@Test
	public void remove() {
		read = controller.service.repository.findByName(name);
		if(read==null)return;
		controller.removeBrand(new MyModel(), read.getId(), new MyRedirectAttributes());
		Assertions.assertThrows(NullPointerException.class, () -> {
			controller.service.read(read.getId()).getId();
		  });
	}
	
}
