package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-28.
 */

@Entity(name = "FurnitureToFurnitureCategory")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToFurnitureCategory implements Serializable {


    private static final long serialVersionUID = -3403046483143769375L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "f_id")
    private Integer fid;

    private double width;

    private double length;

    private double height;

    private String title;

    private String description;

    private int grade;

    private String manufactureAt;

    private String createAt;

    @OneToMany(mappedBy = "fid")
    private List<FurnitureCategory> furnitureCategories;

}
