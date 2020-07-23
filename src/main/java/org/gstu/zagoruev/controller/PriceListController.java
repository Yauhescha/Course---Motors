package org.gstu.zagoruev.controller;

import java.util.ArrayList;
import java.util.List;

import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/priceList")
public class PriceListController {

	@Autowired
	CategoryService service;

	@GetMapping
	public String getpriceList(Model model) {
		List<Category> findAll = service.repository.findAll();
		if (!findAll.isEmpty()) {
			model.addAttribute("categories", findAll);
			model.addAttribute("categoriesFoList", findAll);}
		return "priceList";
	}

	@PostMapping
	public String setPriceList(Model model, @Param("id") long id) {
		List<Category> findAll = service.repository.findAll();
		if (!findAll.isEmpty())
			model.addAttribute("categories", findAll);
		List<Category> list = new ArrayList<Category>();
		list.add(service.read(id));
		if (!list.isEmpty())
			model.addAttribute("categoriesFoList", list);
		return "priceList";
	}

}
