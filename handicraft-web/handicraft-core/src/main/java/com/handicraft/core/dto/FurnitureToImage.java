package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 고승빈 on 2017-08-06.
 */

@Entity(name = "FurnitureToImage")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToImage extends  FurnitureAbs{



    @OneToMany( fetch = FetchType.EAGER ,cascade= CascadeType.ALL)
    @JoinColumn(name = "fid" )
    private List<Image> imageList;


    public FurnitureToImage(Furniture furniture) {

        this.fid = furniture.getFid();
        this.brand = furniture.getBrand();
        this.closed = furniture.isClosed();
        this.createAt = furniture.getCreateAt();
        this.description = furniture.getDescription();
        this.grade = furniture.getGrade();
        this.height = furniture.getHeight();
        this.length = furniture.getLength();
        this.location = furniture.getLocation();
        this.periodOfUse = furniture.getPeriodOfUse();
        this.price = furniture.getPrice();
        this.state = furniture.getState();
        this.title = furniture.getTitle();
        this.type = furniture.getType();
        this.updateAt = furniture.getUpdateAt();
        this.width = furniture.getWidth();
        this.sellerPhone = furniture.getSellerPhone();
        this.sellerKakao = furniture.getSellerKakao();
    }
}
