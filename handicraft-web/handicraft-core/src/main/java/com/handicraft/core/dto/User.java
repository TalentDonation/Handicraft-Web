package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{

	private static final long serialVersionUID = -6625860888600498405L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int mid;

	@Getter @Setter
	String user_id;
	@Getter @Setter
	String password;
	@Getter @Setter
	String name;
	@Getter @Setter
	String address;
	@Getter @Setter
	String phone;
	@Getter @Setter
	String register_date;
	

}
