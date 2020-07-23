package org.gstu.zagoruev.repository;

import java.util.List;

import org.gstu.zagoruev.entity.Warhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarhouseRepository extends  JpaRepository<Warhouse, Long>{
	public Warhouse findByProductId(Long id);
	public List<Warhouse> findByCountGreaterThan(int moreThan);	
}
