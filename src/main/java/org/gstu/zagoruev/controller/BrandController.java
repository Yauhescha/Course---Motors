package org.gstu.zagoruev.controller;

import java.util.List;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService service;

    @GetMapping
    public String getBrand(Model model) {
    	List<Brand> findAll = service.repository.findAll();
        if (!findAll.isEmpty())
			model.addAttribute("brands", findAll);
        return "brand";
    }
    
    @PostMapping
    public String setBrand(Model model, @Param("name") String name,RedirectAttributes ra) {
    	String response;
        System.out.println("start create Brand");
        try {
        	Brand brand = new Brand();
        	brand.setName(name);
			service.create(brand);
			response = "Success created" ;
			ra.addFlashAttribute("response1", response);
		} catch (Exception e) {
			e.printStackTrace();
			response = "Creating failed";
			ra.addFlashAttribute("response1", response);
		}       
        System.out.println("create Brand: "+response);        
        return "redirect:/brand";
    }
    
    @GetMapping("/remove/{id}")
    public String removeBrand(Model model, @PathVariable Long id,RedirectAttributes ra) {
    	try {
    		System.out.println(id);
    		service.repository.delete(service.read(id));
    		ra.addFlashAttribute("response1", "Succes removed");
    		}catch(Exception ex) {ra.addFlashAttribute("response1", "Removing failed");}
    	return "redirect:/brand";
    }
    @PostMapping("/edit")
    public String editBrand(@Param("name") String name, @Param("id") int id,RedirectAttributes ra) {
    	try {
	    	Brand read = service.read(id);
	    	read.setName(name);
			service.update(read);
			ra.addFlashAttribute("response1", "Succes edited");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("response1", "Editing failed");
		}
    	return "redirect:/brand";
    }
}
