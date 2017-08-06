package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Entity(name = "Furniture")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Furniture extends FurnitureAbs implements Serializable {

    private static final long serialVersionUID = -3750423939072711694L;


    @Transient
    private List<String> images;

    public Furniture(FurnitureToImage furnitureToImage) {

        this.fid = furnitureToImage.getFid();
        this.width = furnitureToImage.getWidth();
        this.length = furnitureToImage.getLength();
        this.height = furnitureToImage.getHeight();
        this.title = furnitureToImage.getTitle();
        this.description = furnitureToImage.getDescription();
        this.brand = furnitureToImage.getBrand();
        this.grade = furnitureToImage.getGrade();
        this.state = furnitureToImage.getState();
        this.type = furnitureToImage.getType();
        this.lat = furnitureToImage.getLat();
        this.lon = furnitureToImage.getLon();
        this.periodOfUse = furnitureToImage.getPeriodOfUse();
        this.createAt = furnitureToImage.getCreateAt();

    }

    //    @OneToMany( fetch = FetchType.LAZY  ,cascade= javax.persistence.CascadeType.ALL)
//    @JoinColumn(name = "f_id" , nullable = false)
//    private List<FurnitureCategory> furnitureCategories;

//    @OneToMany( fetch = FetchType.EAGER ,cascade= javax.persistence.CascadeType.ALL)
//    @JoinColumn(name = "f_id" , nullable = false)
//    private List<Image> images;


}
