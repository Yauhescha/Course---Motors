package org.gstu.zagoruev.controller;

import org.gstu.zagoruev.service.BrandService;
import org.gstu.zagoruev.service.CategoryService;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	BrandService brandService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ListService listService;
	@Autowired
	ProductService productService;

	@GetMapping
	public String getindex(Model model) {
		listService.makeModel(model);
		model.addAttribute("products", productService.repository.findAll());
		return "index";
	}

	@GetMapping("/cat{cat}")
	public String getindex1(Model model, @PathVariable(required = false) Long cat) {
		listService.makeModel(model);
		model.addAttribute("names",categoryService.read(cat).getName());
		if (cat != null)
			model.addAttribute("products", categoryService.read(cat).getProduct());
		return "index";
	}

	@GetMapping("/bran{brand}")
	public String getindex2(Model model, @PathVariable(required = false) Long brand) {
		listService.makeModel(model);
		model.addAttribute("names",brandService.read(brand).getName());
		if (brand != null)
			model.addAttribute("products", brandService.read(brand).getProduct());
		return "index";
	}

}
