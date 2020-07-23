package org.gstu.zagoruev.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Table
@Entity
@Data
public class Warhouse extends AbstractEntity {
	@Column
	@NotNull
	Long productId;
	@Column
	@NotNull
	int count;
	
	String productName;
}
