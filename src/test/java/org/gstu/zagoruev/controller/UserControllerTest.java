package org.gstu.zagoruev.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gstu.zagoruev.MyModel;
import org.gstu.zagoruev.MyRedirectAttributes;
import org.gstu.zagoruev.entity.MyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {
	@Autowired
	private UserController controller;

	String address = "addressForTest";
	String addressEdit = "addressForTestEdit";
	String fio = "fioForTest";
	String fioEdit = "fioForTestEdit";
	MyUser read;

	@Test
	public void contexLoads() throws Exception {
		assertNotNull(controller);
	}

	@Test
	public void create() {
		if (controller.service.repository.findByUsername(fio) == null) {
			MyUser temp = new MyUser();
			temp.setFio("test");
			temp.setAddress(address);
			temp.setPhone("test");
			temp.setUsername(fio);
			temp.setPassword("test");
			controller.setUser(new MyModel(), temp, new MyRedirectAttributes());
			read = controller.service.repository.findByUsername(fio);
			assertEquals(fio, read.getUsername());
			assertEquals(address, read.getAddress());
		}
	}

	@Test
	public void update() {
		if (read != null) {
			read = controller.service.repository.findByUsername(fio);
			MyUser temp = new MyUser();
			temp.setUsername(fioEdit);
			controller.editManager(temp, read.getId().intValue(), new MyRedirectAttributes());
			assertEquals(fioEdit, read.getUsername());
		}

	}

	@Test
	public void remove() {
		read = controller.service.repository.findByUsername(fio);
		if (read != null) {
			controller.removeManager(new MyModel(), read.getId(), new MyRedirectAttributes());
			Assertions.assertThrows(NullPointerException.class, () -> {
				controller.service.read(read.getId()).getId();
			});
		}
	}

}
