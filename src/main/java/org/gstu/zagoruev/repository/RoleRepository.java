package org.gstu.zagoruev.repository;

import java.util.ArrayList;

import org.gstu.zagoruev.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends  JpaRepository<Role, Long>{
	public Role findByName(String name);
	public ArrayList<Role> findAll();
}
