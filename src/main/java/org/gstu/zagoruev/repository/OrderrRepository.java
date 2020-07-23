package org.gstu.zagoruev.repository;

import java.sql.Date;
import java.util.List;

import org.gstu.zagoruev.entity.Orderr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderrRepository extends  JpaRepository<Orderr, Long>{
//	public List<Orderr> findByIspolnenAndDateBetween(Boolean ispolnen, Date from, Date to);
	public List<Orderr> findByIspolnenAndDateLessThanEqualAndDateGreaterThanEqual(Boolean ispolnen, Date from, Date to);
	
}
