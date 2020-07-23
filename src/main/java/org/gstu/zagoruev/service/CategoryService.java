package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Category;
import org.gstu.zagoruev.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudImpl<Category> {
	public CategoryRepository repository;

	@Autowired
	public CategoryService(CategoryRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
