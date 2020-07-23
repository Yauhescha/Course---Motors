package org.gstu.zagoruev.repository;

import org.gstu.zagoruev.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends  JpaRepository<Brand, Long>{
	public Brand findByName(String name);
}
