package org.gstu.zagoruev.repository;

import org.gstu.zagoruev.entity.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductRepository extends  JpaRepository<OrdersProduct, Long>{
	public OrdersProduct findByPrice(float price);
}
