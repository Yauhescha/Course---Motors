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
public class Product extends AbstractEntity {
	@Column
	@NotNull
	String name;

	@Column
	@NotNull
	float price;
	
	@Column
	@NotNull
	int quantity;
	
	@Column
	@NotNull
	String description;
	
	String filename;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
	Category category;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
	Producer producer;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
	Brand brand;
	
}
