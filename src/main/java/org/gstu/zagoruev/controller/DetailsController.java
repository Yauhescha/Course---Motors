package org.gstu.zagoruev.controller;

import java.util.List;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.entity.Warhouse;
import org.gstu.zagoruev.service.BrandService;
import org.gstu.zagoruev.service.CategoryService;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.WarhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/details")
public class DetailsController {
	@Autowired
    ListService listService;
	@Autowired
	ProductService productService;
	@Autowired
	WarhouseService warhouseService;

	@GetMapping("/{id}")
	public String getindex(Model model, @PathVariable Long id) {
		listService.makeModel(model);
		model.addAttribute("product", productService.read(id));
		Warhouse warhouse = warhouseService.repository.findByProductId(id);
		int count = (warhouse == null) ? 0 : warhouse.getCount();
		model.addAttribute("count", count);
		return "details";
	}

}
