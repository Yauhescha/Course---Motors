package org.gstu.zagoruev.controller;

import java.util.List;

import org.gstu.zagoruev.entity.Producer;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    ProducerService service;
    @Autowired
    ListService listService;

    @GetMapping
    public String getProducer(Model model) {
    	listService.makeModel(model);
    	List<Producer> findAll = service.repository.findAll();
        if (!findAll.isEmpty())
			model.addAttribute("producers", findAll);
        return "producer";
    }
    
    @PostMapping
    public String setProducer(Model model, @ModelAttribute Producer producer,RedirectAttributes ra) {
    	String response;
        System.out.println("start create producer");
        try {
			service.create(producer);
			response = "Success created" ;
			ra.addFlashAttribute("response1", "Producer was created");
		} catch (Exception e) {
//			e.printStackTrace();
			response = "creating failed";
			ra.addFlashAttribute("response1", "Creating producer failed");
		}       
        System.out.println("create producer: "+response);
        
        List<Producer> findAll = service.repository.findAll();
        if (!findAll.isEmpty())
			model.addAttribute("producers", findAll);
        return "redirect:/producer";
    }
    @PostMapping("/edit")
    public String editProducer(@ModelAttribute Producer producer, @Param("idd") int idd,RedirectAttributes ra) {
    	try {
    		Producer read = service.read(idd);
    		read.setAddress(producer.getAddress());
    		read.setPhone(producer.getPhone());
    		read.setTitle(producer.getTitle());
			service.update(read);
			ra.addFlashAttribute("response1", "Producer was edited");
		} catch (Exception e) {
//			e.printStackTrace();
			ra.addFlashAttribute("response1", "Editing producer failed");
		}       
        return "redirect:/producer";
    }
    
    @GetMapping("/remove/{id}")
    public String removeProducer(Model model, @PathVariable Long id,RedirectAttributes ra) {
    	try {
    	System.out.println(id);
    	service.repository.delete(service.read(id));
    	ra.addFlashAttribute("response1", "Producer was deleted");
    	}catch (Exception e) {
    		ra.addFlashAttribute("response1", "Deleting producer failed");
		}
    	return "redirect:/producer";
    }
}
