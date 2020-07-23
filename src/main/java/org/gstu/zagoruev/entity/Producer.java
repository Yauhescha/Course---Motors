package org.gstu.zagoruev.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table
@Data
public class Producer extends AbstractEntity {
	@Column
	@NotNull

	String title;
	@Column
	@NotNull
	String phone;
	@Column
	@NotNull
	String address;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "producer", cascade = CascadeType.ALL)
	private List<Product> product = new ArrayList<Product>();

}
