package org.gstu.zagoruev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gstu.zagoruev.MyModel;
import org.gstu.zagoruev.MyRedirectAttributes;
import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CategoryControllerTest {
	@Autowired
	private CategoryController controller;

	String name="nameForTest";
	String nameEdit="nameForTestEdit";
	Category read;
	@Test
	public void contexLoads() throws Exception {
		assertNotNull(controller);
	}
	
	@Test
	public void create() {
		if(controller.service.repository.findByName(name)!=null) return;
		Category category = new Category();
		category.setName(name);
		controller.setcategory(new MyModel(), category, new MyRedirectAttributes());
		read = controller.service.repository.findByName(name);
		assertEquals(name, read.getName());
	}
	@Test
	public void update() {
		if(read==null)return;
		read = controller.service.repository.findByName(name);
		Category temp = new Category();
		temp.setName(name);
		controller.editcategory(temp, read.getId().intValue(), new MyRedirectAttributes());
		assertEquals(nameEdit, read.getName());
		
		
	}
	@Test
	public void remove() {
		read = controller.service.repository.findByName(name);
		if(read==null)return;
		controller.removecategory(new MyModel(), read.getId(), new MyRedirectAttributes());
		Assertions.assertThrows(NullPointerException.class, () -> {
			controller.service.read(read.getId()).getId();
		  });
	}
	
}
