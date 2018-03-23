package com.handicraft.core.domain;

import com.handicraft.core.dto.FurnitureDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class Furniture implements Serializable {
    private static final long serialVersionUID = -5165832301683753860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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

    @LastModifiedDate
    private ZonedDateTime updateAt;

    @CreatedDate
    private ZonedDateTime createAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "furniture")
    private List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Furniture() {
    }

    public Furniture(FurnitureDto furnitureDto) {
        this.fid = furnitureDto.getFid();
        this.title = furnitureDto.getTitle();
        this.state = furnitureDto.getState();
        this.grade = furnitureDto.getGrade();
        this.description = furnitureDto.getDescription();
        this.width = furnitureDto.getWidth();
        this.length = furnitureDto.getLength();
        this.height = furnitureDto.getHeight();
        this.location = furnitureDto.getLocation();
        this.type = furnitureDto.getType();
        this.sellerPhone = furnitureDto.getSellerPhone();
        this.sellerKakao = furnitureDto.getSellerKakao();
        this.price = furnitureDto.getPrice();
        this.periodOfUse = furnitureDto.getPeriodOfUse();
        this.brand = furnitureDto.getBrand();
        this.closed = furnitureDto.isClosed();
        this.updateAt = furnitureDto.getUpdateAt();
        this.createAt = furnitureDto.getCreateAt();
    }
}
