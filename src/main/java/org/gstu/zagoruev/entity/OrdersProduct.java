package org.gstu.zagoruev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table
@Data
public class OrdersProduct extends AbstractEntity {
	@Column
	@NotNull
	Long productId;
	@Column
	@NotNull
	int Quantity;
	
	String productName;
	String filename;
	float price;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
	Orderr orderr;

}
