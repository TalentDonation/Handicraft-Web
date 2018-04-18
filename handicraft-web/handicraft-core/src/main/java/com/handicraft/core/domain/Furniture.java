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
@Table(name = "furniture" , indexes = {
        @Index(name = "furniture_idx_title", columnList = "title")
})
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class Furniture implements Serializable {
    private static final long serialVersionUID = -5165832301683753860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid", nullable = false)
    private long fid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "width", nullable = false)
    private double width;

    @Column(name = "length", nullable = false)
    private double length;

    @Column(name = "height", nullable = false)
    private double height;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "seller_phone")
    private String sellerPhone;

    @Column(name = "seller_kakao")
    private String sellerKakao;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "period_of_use", nullable = false)
    private String periodOfUse;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "closed", nullable = false)
    private boolean closed;

    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;

    @CreatedDate
    @Column(name = "create_at", nullable = false)
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
