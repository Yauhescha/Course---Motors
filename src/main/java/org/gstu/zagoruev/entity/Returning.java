package org.gstu.zagoruev.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Returning extends AbstractEntity {
	Long userId;
	@Column(unique = true)
	Long orderProductId;
	Date date;
	Boolean approve=false;

}
