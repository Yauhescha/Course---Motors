package org.gstu.zagoruev.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table
@Entity
public class Orderr extends AbstractEntity {
	@Column
	float price;
	@Column
	Date date= new Date(System.currentTimeMillis());
	@Column
	Boolean sakonshen = false;
	@Column
	Boolean ispolnen = false;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	MyUser user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderr", cascade = CascadeType.ALL)
	List<OrdersProduct> products = new ArrayList<OrdersProduct>();

	@Override
	public String toString() {
		return "Orderr [price=" + price + ", date=" + date + ", sakonshen=" + sakonshen + ", ispolnen=" + ispolnen
				+ ", user=" + user.getFio() + ", products=" + products.size() + "]";
	}
}
