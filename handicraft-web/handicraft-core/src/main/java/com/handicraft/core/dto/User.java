package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "User")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends UserAbs implements  Serializable{

	// ignore lombok
	private static final long serialVersionUID = -6625860888600498405L;


	@Transient
	private String avatar;

	public User(UserToImage userToImage)
	{
		this.uid = userToImage.getUid();
		this.address = userToImage.getAddress();
		this.birthday = userToImage.getBirthday();
		this.joinAt = userToImage.getJoinAt();
		this.name = userToImage.getName();
		this.nickname = userToImage.getNickname();
		this.phone = userToImage.getPhone();
	}
}
