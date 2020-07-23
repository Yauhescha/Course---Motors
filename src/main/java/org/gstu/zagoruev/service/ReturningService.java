package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Returning;
import org.gstu.zagoruev.repository.ReturningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturningService extends CrudImpl<Returning> {
	public ReturningRepository repository;

	@Autowired
	public ReturningService(ReturningRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
