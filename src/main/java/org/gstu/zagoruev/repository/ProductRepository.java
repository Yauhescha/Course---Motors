package org.gstu.zagoruev.repository;

import java.util.List;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.entity.Producer;
import org.gstu.zagoruev.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends  JpaRepository<Product, Long>{
	public List<Product> findByNameContaining(String name);
	public List<Product> findByPrice(float price);
	public List<Product> findByQuantity(int quantity);
	public List<Product> findByDescriptionContaining(String description);
	public List<Product> findByCategory(Category ategory);
	public List<Product> findByProducer(Producer producer);
	public List<Product> findByBrand(Brand brand);
}
