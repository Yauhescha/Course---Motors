package org.gstu.zagoruev.controller;

import org.gstu.zagoruev.entity.MyUser;
import org.gstu.zagoruev.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
public class RegisterController {

    @Autowired
    MyUserService service;

    @GetMapping
    public String getRegistration() {
        return "register";
    }
    
    @PostMapping
    public String setRegistrationForm(Model model, @ModelAttribute MyUser customer,RedirectAttributes redirectAttributes) {
    	
        System.out.println("start registration");
        boolean success = service.register(customer);
        String response = success ? "Success registration" : "Registration failed";
        System.out.println("registration users: "+response);
        redirectAttributes.addFlashAttribute("success", response);
        return "redirect:/";
    }
}
