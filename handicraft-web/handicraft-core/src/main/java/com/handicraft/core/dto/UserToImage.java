package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.service.UserToImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "UserToImage")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToImage extends UserAbs implements Serializable {


    private static final long serialVersionUID = -6625860888600498425L;

    @OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private Image image;


    public UserToImage(User user)
    {
        this.uid = user.getUid();
        this.address = user.getAddress();
        this.birthday = user.getBirthday();
        this.joinAt = user.getJoinAt();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
    }

}
