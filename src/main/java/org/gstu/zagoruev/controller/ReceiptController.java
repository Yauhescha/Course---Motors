package org.gstu.zagoruev.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gstu.zagoruev.entity.MyUser;
import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.MyUserService;
import org.gstu.zagoruev.service.OrderrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {
	@Autowired
	OrderrService orderService;
	@Autowired
    ListService listService;
	@Autowired
	MyUserService userService;

	@GetMapping
	public String getReceipt(Model model) {
		listService.makeModel(model);
		Date to = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(to);
		c.add(Calendar.MONTH, -1);
		Date from = new Date(c.getTimeInMillis());
		List<Orderr> list = orderService.repository.findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to, from);
		System.out.println(list);
		model.addAttribute("list", list);
		getUsers(model);
		return "receipt";
	}
	public void getUsers(Model model) {
		List<MyUser> users = userService.repository.findAll();
		model.addAttribute("users", users);		
	}
	@GetMapping("/find")
	public String getReceiptFromDate(Model model, @Param("from") Date from, @Param("to") Date to) {
		listService.makeModel(model);
		List<Orderr> list = orderService.repository.findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to, from);
		model.addAttribute("list", list);
		getUsers(model);
		return "receipt";
	}
	@GetMapping("/findUser")
	public String getReceiptFromDateAndUser(Model model, @Param("from") Date from, @Param("to") Date to, @Param("id") long id) {
		
		listService.makeModel(model);
		List<Orderr> list = orderService.repository.findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean.TRUE, to, from);
		List<Orderr> userList = new ArrayList<Orderr>();
		for(Orderr order:list)
			if(order.getUser().getId()==id)
				userList.add(order);
		model.addAttribute("list", userList);
		getUsers(model);
		return "receipt";
	}

}
