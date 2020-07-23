package org.gstu.zagoruev.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table
@Data
public class Role extends AbstractEntity {

	@Column(unique = true)
	private String name;
	@JsonIgnore
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<MyUser> users = new ArrayList<MyUser>();

	public Role(final String name) {
		super();
		this.name = name;
	}

	public Role() {
		super();
	}

}
