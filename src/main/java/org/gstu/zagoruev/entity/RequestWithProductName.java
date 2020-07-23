package org.gstu.zagoruev.entity;

import lombok.Data;

@Data
public class RequestWithProductName {
	Long id;
	int count;
	Long productId;
	String name;
	public RequestWithProductName(Long id, int count, Long productId, String name) {
		super();
		this.id = id;
		this.count = count;
		this.productId = productId;
		this.name = name;
	}
}
