package org.gstu.zagoruev.repository;

import java.util.List;

import org.gstu.zagoruev.entity.Returning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturningRepository extends  JpaRepository<Returning, Long>{
	public List<Returning> findByApprove(Boolean approve);
	public Returning findByOrderProductId(Long orderProductId);
}
