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



    @OneToMany( fetch = FetchType.EAGER ,cascade= javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "f_id" , nullable = false)
    private List<Image> imageList;


    public FurnitureToImage(Furniture furniture) {

        this.fid = furniture.getFid();
        this.width = furniture.getWidth();
        this.length = furniture.getLength();
        this.height = furniture.getHeight();
        this.title = furniture.getTitle();
        this.description = furniture.getDescription();
        this.brand = furniture.getBrand();
        this.grade = furniture.getGrade();
        this.state = furniture.getState();
        this.location = furniture.getLocation();
        this.lat = furniture.getLat();
        this.lon = furniture.getLon();
        this.type = furniture.getType();
        this.sold = furniture.isSold();
        this.price = furniture.getPrice();
        this.info = furniture.getInfo();
        this.periodOfUse = furniture.getPeriodOfUse();
        this.purchaseAt = furniture.getPurchaseAt();
        this.updateAt = furniture.getUpdateAt();
        this.createAt = furniture.getCreateAt();
    }
}
