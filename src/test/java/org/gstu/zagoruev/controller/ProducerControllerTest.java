package org.gstu.zagoruev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gstu.zagoruev.MyModel;
import org.gstu.zagoruev.MyRedirectAttributes;
import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.entity.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProducerControllerTest {
	@Autowired
	private ProducerController controller;

	String name="nameForTest";
	String nameEdit="nameForTestEdit";
	Producer read;
	@Test
	public void contexLoads() throws Exception {
		assertNotNull(controller);
	}
	
	@Test
	public void create() {
		if(controller.service.repository.findByTitle(name)!=null) return;
		Producer temp = new Producer();
		temp.setTitle(name);
		temp.setPhone(name);
		temp.setAddress(name);
		controller.setProducer(new MyModel(), temp, new MyRedirectAttributes());
		read = controller.service.repository.findByTitle(name);
		assertEquals(name, read.getTitle());
	}
	@Test
	public void update() {
		if(read==null)return;
		read = controller.service.repository.findByTitle(name);
		Producer temp = new Producer();
		temp.setTitle(nameEdit);
		temp.setPhone(nameEdit);
		temp.setAddress(nameEdit);
		controller.editProducer(temp, read.getId().intValue(), new MyRedirectAttributes());
		assertEquals(nameEdit, read.getTitle());
		
		
	}
	@Test
	public void remove() {
		read = controller.service.repository.findByTitle(name);
		if(read==null)return;
		controller.removeProducer(new MyModel(), read.getId(), new MyRedirectAttributes());
		Assertions.assertThrows(NullPointerException.class, () -> {
			controller.service.read(read.getId()).getId();
		  });
	}
	
}
