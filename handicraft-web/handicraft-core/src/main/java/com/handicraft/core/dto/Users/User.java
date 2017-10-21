package com.handicraft.core.dto.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.dto.Users.UserAbs;
import com.handicraft.core.dto.Users.UserToImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends UserAbs implements  Serializable{

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
