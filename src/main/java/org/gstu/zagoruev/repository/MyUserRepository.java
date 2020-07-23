package org.gstu.zagoruev.repository;

import org.gstu.zagoruev.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends  JpaRepository<MyUser, Long>{
	public MyUser findByUsername(String fio);
}
