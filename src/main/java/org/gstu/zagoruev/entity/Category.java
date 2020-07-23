package org.gstu.zagoruev.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table
@Data
public class Category extends AbstractEntity{

	String name;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="category", cascade=CascadeType.ALL)
    private List<Product> product;
}
