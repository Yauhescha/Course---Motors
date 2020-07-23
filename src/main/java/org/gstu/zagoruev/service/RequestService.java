package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Request;
import org.gstu.zagoruev.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService extends CrudImpl<Request> {
	public RequestRepository repository;

	@Autowired
	public RequestService(RequestRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
