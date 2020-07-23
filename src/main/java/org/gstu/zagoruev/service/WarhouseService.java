package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.Warhouse;
import org.gstu.zagoruev.repository.WarhouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarhouseService extends CrudImpl<Warhouse> {
	public WarhouseRepository repository;

	@Autowired
	public WarhouseService(WarhouseRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
