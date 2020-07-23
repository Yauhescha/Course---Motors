package org.gstu.zagoruev.controller;

import java.util.ArrayList;
import java.util.List;

import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.entity.Request;
import org.gstu.zagoruev.entity.RequestWithProductName;
import org.gstu.zagoruev.entity.Warhouse;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.ProductService;
import org.gstu.zagoruev.service.RequestService;
import org.gstu.zagoruev.service.WarhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService service;
    @Autowired
    ProductService productService;
    @Autowired
    WarhouseService warhouserService;
    @Autowired
    ListService listService;
    

    @GetMapping
    public String getRequest(Model model) {
    	listService.makeModel(model);
    	List<Request> findAll = service.repository.findAll();
    	List<RequestWithProductName> newRec = new ArrayList<RequestWithProductName>();
        if (!findAll.isEmpty()) 
        	for(int i=0; i<findAll.size();i++)
        		if(!findAll.get(i).getAprove()) {
        			newRec.add(new RequestWithProductName(findAll.get(i).getId(), 
        					findAll.get(i).getCount(), findAll.get(i).getProductId(), 
        					productService.read(findAll.get(i).getProductId()).getName()));
        		}
        	
			model.addAttribute("requests", newRec);
        
        List<Product> products= productService.repository.findAll();
        if (!products.isEmpty())
			model.addAttribute("products", products);
        
        return "request";
    }
    
    @PostMapping
    public String setRequest(Model model, @ModelAttribute Request brand) {
    	String response;
        System.out.println("start create Request");
        try {
			service.create(brand);
			response = "Success created" ;
		} catch (Exception e) {
			e.printStackTrace();
			response = "creating failed";
		}       
        System.out.println("create Request: "+response);        
       
        return "redirect:/request";
    }
    
    @GetMapping("/remove/{id}")
    public String removeRequest(Model model, @PathVariable Long id) {
    	service.repository.delete(service.read(id));
    	return "redirect:/request";
    }
    @GetMapping("/aprove/{id}")
    public String aproveRequest(Model model, @PathVariable Long id) throws Exception {
    	
    	Request req = service.read(id);
    	req.setAprove(true);
    	
    	Product product = productService.read(req.getProductId());
    	
    	Warhouse warh = warhouserService.repository.findByProductId(req.getProductId());
    	if(warh==null) {
    		warh= new Warhouse();
    		warh.setProductId(req.getProductId());
    		warh.setCount(req.getCount());
    		warh.setProductName(product.getName());
    		warhouserService.create(warh);
    	}
    	else {
    		warh.setCount(warh.getCount()+req.getCount());
    		warhouserService.update(warh);
    	}
    		
    	return "redirect:/request";
    }
}
