package org.gstu.zagoruev.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Table
@Entity
@Data
public class Request extends AbstractEntity {
	@Column
	@NotNull
	Long productId;
	@Column
	@NotNull
	int count;
	@Column
	@NotNull
	Date date = new Date(System.currentTimeMillis());
	
	@Column
	@NotNull
	Boolean aprove=false;
}
