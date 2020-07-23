package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Producer;
import org.gstu.zagoruev.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService extends CrudImpl<Producer> {
	public ProducerRepository repository;
	@Autowired
	public ProducerService(ProducerRepository repository) {
	super(repository);
	this.repository=repository;
	}
}
