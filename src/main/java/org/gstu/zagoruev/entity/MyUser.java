package org.gstu.zagoruev.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table
@Data
public class MyUser extends AbstractEntity {
	@Column
	@NotNull
	private String fio;

	@Column
	@NotNull
	private String address;

	@Column
	@NotNull
	private String phone;

	@Column(unique = true)
	@NotNull
	private String username;

	@Column
	@JsonIgnore
	@NotNull
	private String password;

	@JsonIgnore
	@Fetch(value = FetchMode.SELECT)
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = @UniqueConstraint(name = "customers_roles", columnNames = {
			"user_id", "role_id" }))
	private List<Role> roles = new ArrayList<Role>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Orderr> orderr = new ArrayList<Orderr>();

	public List<String> getRoleListNames() {
		List<String> roleNames = new ArrayList<>();
		for (Role currRole : getRoles()) {
			roleNames.add(currRole.getName());
		}
		return roleNames;
	}
}
