package org.gstu.zagoruev.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class ReportHelper {
	String productName;
	float productPrice;
	int count;
	Date date;
	String producerName;
	Long productId;
	public ReportHelper(String productName, float productPrice, int count, Date date, String producerName) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.count = count;
		this.date = date;
		this.producerName = producerName;
	}
	public ReportHelper(String productName, float productPrice, int count, Date date, String producerName, Long productId) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.count = count;
		this.date = date;
		this.producerName = producerName;
		this.productId=productId;
	}
	
}
