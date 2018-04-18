package com.handicraft.core.dto;

import com.handicraft.core.domain.Furniture;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class FurnitureDto implements Serializable {
    private static final long serialVersionUID = -6971244331672376764L;

    private long fid;
    private String title;
    private String state;
    private String grade;
    private String description;
    private double width;
    private double length;
    private double height;
    private String location;
    private String type;
    private String sellerPhone;
    private String sellerKakao;
    private String price;
    private String periodOfUse;
    private String brand;
    private boolean closed;
    private ZonedDateTime updateAt;
    private ZonedDateTime createAt;
    private long uid;

    public FurnitureDto() {
    }

    public FurnitureDto(Furniture furniture) {
        this.fid = furniture.getFid();
        this.title = furniture.getTitle();
        this.state = furniture.getState();
        this.grade = furniture.getGrade();
        this.description = furniture.getDescription();
        this.width = furniture.getWidth();
        this.length = furniture.getLength();
        this.height = furniture.getHeight();
        this.location = furniture.getLocation();
        this.type = furniture.getType();
        this.sellerPhone = furniture.getSellerPhone();
        this.sellerKakao = furniture.getSellerKakao();
        this.price = furniture.getPrice();
        this.periodOfUse = furniture.getPeriodOfUse();
        this.brand = furniture.getBrand();
        this.closed = furniture.isClosed();
        this.updateAt = furniture.getUpdateAt();
        this.createAt = furniture.getCreateAt();
        this.uid = furniture.getUser().getUid();
    }
}
