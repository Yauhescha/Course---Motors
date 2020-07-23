package org.gstu.zagoruev.entity;

import lombok.Data;

@Data
public class ChartHelper {
	Long productId;
	int count;
	int warhouse;
	public ChartHelper(Long productId, int count, int warhouse) {
		super();
		this.productId = productId;
		this.count = count;
		this.warhouse = warhouse;
	}
	public ChartHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
}
