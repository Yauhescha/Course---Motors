package org.gstu.zagoruev.repository;

import java.sql.Date;
import java.util.List;

import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends  JpaRepository<Request, Long>{
	public Request findByProductId(Long id);
	public List<Request> findByAproveAndDateLessThanEqualAndDateGreaterThanEqual(Boolean ispolnen, Date from, Date to);
	
}
