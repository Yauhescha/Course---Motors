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
@RequestMapping("/user")
public class UserController {

    @Autowired
    MyUserService service;
    @Autowired
    RoleService roleService;
    @Autowired
    ListService listService;

    @GetMapping
    public String geUser(Model model) {
    	listService.makeModel(model);
    	Role roleU = roleService.findByName("ROLE_USER");
    	Role roleM = roleService.findByName("ROLE_MANAGER");
    	List<MyUser> findAll = service.repository.findAll();
        if (!findAll.isEmpty()) {
        	List<MyUser> list = new ArrayList<MyUser>();
        	for(int i=0; i<findAll.size();i++)
        		if(findAll.get(i).getRoles().contains(roleU) && !findAll.get(i).getRoles().contains(roleM))
        		list.add(findAll.get(i));
        	model.addAttribute("managers", list);
        }
        return "user";
    }
    
    @PostMapping
    public String setUser(Model model, @ModelAttribute MyUser manager, RedirectAttributes ra) {
    	String response="error";
        try {
			service.register(manager);
			ra.addFlashAttribute("response1","User was added");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("response1","Adding user failed");
		}       
        
        return "redirect:/user";
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
    		ra.addFlashAttribute("response1","User was edited");
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("response1","Editing user failed");
		}       
        
        return "redirect:/user";
    }
    @GetMapping("/remove/{id}")
    public String removeManager(Model model, @PathVariable Long id, RedirectAttributes ra) {
    	try {
    	System.out.println(id);
    	service.repository.delete(service.read(id));
    	ra.addFlashAttribute("response1","User was deleted");}
    	catch (Exception e) {
    		ra.addFlashAttribute("response1","Deleting user failed");
		}
    	return "redirect:/user";
    }
    
}
