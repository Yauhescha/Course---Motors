package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Brand;
import org.gstu.zagoruev.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends CrudImpl<Brand> {
	public BrandRepository repository;
	@Autowired
	public BrandService(BrandRepository repository) {
	super(repository);
	this.repository=repository;
	}
}
