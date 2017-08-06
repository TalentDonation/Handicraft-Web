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

@Entity(name = "Furniture")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id" , nullable = false)
    private long fid;

    private double width;

    private double length;

    private double height;

    private String title;

    private String description;

    private String brand;

    private String grade;

    private String state;

    private String location;

    private String type;

    private double lat;

    private double lon;

    private int periodOfUse;

    private String createAt;


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
    private List<Image> images;
}
