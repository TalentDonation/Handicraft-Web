package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.utils.enums.Gender;
import com.handicraft.core.utils.converter.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	private int uid;

	private String name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String phone;

	private String address;

	private String feature;

	@LastModifiedDate
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime updateAt;

	@CreatedDate
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime joinAt;

}
