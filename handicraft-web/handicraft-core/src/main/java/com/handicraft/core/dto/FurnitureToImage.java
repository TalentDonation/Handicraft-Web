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






    public FurnitureToImage(Furniture furniture) {

        this.setFid(furniture.getFid());
        this.setWidth(furniture.getWidth());
        this.setHeight(furniture.getHeight());
        this.setTitle(furniture.getTitle());
        this.setDescription(furniture.getDescription());
        this.setBrand(furniture.getBrand());
        this.setGrade(furniture.getGrade());
        this.setState(furniture.getState());
        this.setLocation(furniture.getLocation());
        this.setType(furniture.getType());
        this.setLat(furniture.getLat());
        this.setLon(furniture.getLon());
        this.setPeriodOfUse(furniture.getPeriodOfUse());
        this.setCreateAt(furniture.getCreateAt());

    }

    @OneToMany( fetch = FetchType.EAGER ,cascade= javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "f_id" , nullable = false)
    private List<Image> imageList;
}
