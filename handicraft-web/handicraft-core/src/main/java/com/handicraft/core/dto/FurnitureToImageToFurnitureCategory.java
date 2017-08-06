package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 고승빈 on 2017-08-03.
 */

@Entity(name = "FurnitureToImageToFurnitureCategory")
@Table(name = "furniture")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToImageToFurnitureCategory extends FurnitureAbs implements Serializable{

    private static final long serialVersionUID = 1103779987811335349L;

//    @OneToMany(mappedBy = "fid")
//    private List<FurnitureCategory> furnitureCategories;
//
//    @OneToMany(mappedBy = "fid")
//    private List<Image> images;


}
