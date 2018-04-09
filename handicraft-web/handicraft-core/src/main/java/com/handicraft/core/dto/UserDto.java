package com.handicraft.core.dto;

import com.handicraft.core.domain.Avatar;
import com.handicraft.core.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = -6625860888600498405L;

    public UserDto() {
    }

    public UserDto(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.birthday = user.getBirthday();
        this.joinAt = user.getJoinAt();
    }

    public void makeAvatar(Avatar avatar) {
        if (avatar == null) return;

        this.avatar = "http://www.half-handicraft.com:8080/avatar/" + avatar.getName();
    }

    private long uid;
    private String name;
    private String nickname;
    private String phone;
    private String address;
    private String birthday;
    private ZonedDateTime joinAt;
    private String avatar;

}
