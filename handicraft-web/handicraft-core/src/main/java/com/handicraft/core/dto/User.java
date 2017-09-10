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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
	private long uid;

	private String name;

	private String nickname;

	private String phone;

	private String address;

	private String birthday;

	@Transient
	private String avatar;

//	@Convert(converter = LocalDateTimeAttributeConverter.class)
//	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//	private LocalDateTime joinAt;

	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date joinAt;


}
