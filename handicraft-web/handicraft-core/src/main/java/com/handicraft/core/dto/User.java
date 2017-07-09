package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "User")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{

	// ignore lombok
	private static final long serialVersionUID = -6625860888600498405L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="u_id")
	@NonNull
	private int uid;

	@NonNull
	private String token;

	@Column(name="naver_token")
	private String naverToken;

	private String name;

	private boolean gender;

	private String phone;

	private String address;

	private String feature;

	private String joinAt;

}
