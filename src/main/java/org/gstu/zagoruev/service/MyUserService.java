package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.MyUser;
import org.gstu.zagoruev.entity.Role;
import org.gstu.zagoruev.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService extends CrudImpl<MyUser> {
	@Autowired
    private RoleService roleService;
	
	public MyUserRepository repository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	public MyUserService(MyUserRepository repository) {
		super(repository);
		this.repository=repository;
	}

	public boolean register(MyUser entity) {
		try {			
			 entity.setPassword(passwordEncoder.encode(entity.getPassword()));
			create(entity);
			Role role=roleService.findByName("ROLE_USER");
            entity.getRoles().add(role);
            update(entity);
            role.getUsers().add(entity);
            roleService.update(role);
            
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
