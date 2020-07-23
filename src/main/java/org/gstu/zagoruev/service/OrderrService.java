package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Orderr;
import org.gstu.zagoruev.repository.OrderrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderrService extends CrudImpl<Orderr> {
	public OrderrRepository repository;

	@Autowired
	public OrderrService(OrderrRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
