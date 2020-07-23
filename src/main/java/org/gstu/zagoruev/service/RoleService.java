package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Role;
import org.gstu.zagoruev.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends CrudImpl<Role> {
	RoleRepository repository;

	@Autowired
	public RoleService(RoleRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public Role findByName(String name) {
		return repository.findByName(name);
	}
}
