package org.gstu.zagoruev.service;

import java.util.List;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ListService {
	@Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;

	public void makeModel(Model model) {
		List<Brand> brands = brandService.repository.findAll();
        if (!brands.isEmpty())
			model.addAttribute("brands", brands);
        
        List<Category> categories = categoryService.repository.findAll();
        if (!categories.isEmpty())
			model.addAttribute("categories", categories);
        
	}
}
