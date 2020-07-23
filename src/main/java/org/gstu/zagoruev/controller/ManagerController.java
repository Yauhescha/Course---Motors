package org.gstu.zagoruev.controller;

import java.util.ArrayList;
import java.util.List;

import org.gstu.zagoruev.entity.MyUser;
import org.gstu.zagoruev.entity.Role;
import org.gstu.zagoruev.service.ListService;
import org.gstu.zagoruev.service.MyUserService;
import org.gstu.zagoruev.service.RoleService;
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
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    MyUserService service;
    @Autowired
    RoleService roleService;
    @Autowired
    ListService listService;

    @GetMapping
    public String geManager(Model model) {
    	listService.makeModel(model);
    	Role managerR = roleService.findByName("ROLE_MANAGER");
    	List<MyUser> findAll = service.repository.findAll();
        if (!findAll.isEmpty()) {
        	List<MyUser> list = new ArrayList<MyUser>();
        	for(int i=0; i<findAll.size();i++)
        		if(findAll.get(i).getRoles().contains(managerR))
        		list.add(findAll.get(i));
        	model.addAttribute("managers", list);
        }
        return "manager";
    }
    
    @PostMapping
    public String setManager(Model model, @ModelAttribute MyUser manager, RedirectAttributes ra) {
    	String response="error";
        System.out.println("start create manager");
        try {
			if(service.register(manager)) {
				Role managerR = roleService.findByName("ROLE_MANAGER");
				manager.getRoles().add(managerR);
				service.update(manager);
				managerR.getUsers().add(manager);
				roleService.update(managerR);
				response = "Success created" ;
				ra.addFlashAttribute("response1", response);
			}
		} catch (Exception e) {
//			e.printStackTrace();
//			response = "creating failed";
			ra.addFlashAttribute("response1", "Creating failed");
		}       
        System.out.println("creating manager: "+response);        
        
        return "redirect:/manager";
    }
    
    @PostMapping("/edit")
    public String editManager(@ModelAttribute MyUser manager, @Param("idd") int idd, RedirectAttributes ra) {
    	try {
    		MyUser read = service.read(idd);
    		read.setAddress(manager.getAddress());
    		read.setFio(manager.getFio());
    		read.setPassword(manager.getPassword());
    		read.setPhone(manager.getPhone());
    		read.setUsername(manager.getUsername());
    		service.update(read);
    		ra.addFlashAttribute("response1", "Succes updated");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("response1", "Updating failed");
		}       
        
        return "redirect:/manager";
    }
    @GetMapping("/remove/{id}")
    public String removeManager(Model model, @PathVariable Long id, RedirectAttributes ra) {
    	try {
	    	System.out.println(id);
	    	service.repository.delete(service.read(id));
	    	ra.addFlashAttribute("response1", "Succes removed");
	    	}    	
    	catch (Exception e) {
    		ra.addFlashAttribute("response1", "Removing failed");
		}
    	return "redirect:/manager";
    }
    
}
