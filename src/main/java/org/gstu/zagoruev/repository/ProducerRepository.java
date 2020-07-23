package org.gstu.zagoruev.repository;

import org.gstu.zagoruev.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends  JpaRepository<Producer, Long>{
	public Producer findByTitle(String title); 
}
