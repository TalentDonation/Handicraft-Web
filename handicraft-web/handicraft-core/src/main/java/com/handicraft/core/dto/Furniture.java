package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
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
        this.brand = furnitureToImage.getBrand();
        this.closed = furnitureToImage.isClosed();
        this.createAt = furnitureToImage.getCreateAt();
        this.description = furnitureToImage.getDescription();
        this.grade = furnitureToImage.getGrade();
        this.height = furnitureToImage.getHeight();
        this.info = furnitureToImage.getInfo();
        this.length = furnitureToImage.getLength();
        this.location = furnitureToImage.getLocation();
        this.periodOfUse = furnitureToImage.getPeriodOfUse();
        this.price = furnitureToImage.getPrice();
        this.state = furnitureToImage.getState();
        this.title = furnitureToImage.getTitle();
        this.type = furnitureToImage.getType();
        this.updateAt = furnitureToImage.getUpdateAt();
        this.width = furnitureToImage.getWidth();

    }

}
