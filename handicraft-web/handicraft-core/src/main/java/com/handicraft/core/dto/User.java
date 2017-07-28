package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements  Serializable{

	// ignore lombok
	private static final long serialVersionUID = -6625860888600498405L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "u_id")
	private int uid;

	private String token;

	@Column(name = "naver_token")
	private String naverToken;

	private String name;

	private boolean gender;

	private String phone;

	private String address;

	private String feature;

	private String joinAt;

}
