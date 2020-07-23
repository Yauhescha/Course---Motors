package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Product;
import org.gstu.zagoruev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudImpl<Product> {
	public ProductRepository repository;

	@Autowired
	public ProductService(ProductRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
