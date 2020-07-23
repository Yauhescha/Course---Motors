package org.gstu.zagoruev.service;

import org.gstu.zagoruev.entity.OrdersProduct;
import org.gstu.zagoruev.repository.OrdersProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersProductService extends CrudImpl<OrdersProduct> {
	public OrdersProductRepository repository;

	@Autowired
	public OrdersProductService(OrdersProductRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
