package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "UserToFurniture")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToFurniture extends UserAbs implements Serializable{

    private static final long serialVersionUID = -4625860888600498425L;

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private List<FurnitureToImage> furnitureToImageList;

    public UserToFurniture(User user)
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
