package com.handicraft.core.dto;

import com.handicraft.core.id.FurnitureCategoryId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by 고승빈 on 2017-07-15.
 */

@Entity(name = "FurnitureCategory")
@Table(name = "furniture_category")
@IdClass(FurnitureCategoryId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureCategory{


    @Id
    @Column(name="f_id" )
    private Integer fid;

    @Id
    @Column(name="t_id")
    private Integer tid;

//    @Id
//    @ManyToOne
//    @JoinColumn(name="f_id")
//    private Furniture furniture;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name="t_id")
//    private Category category;



}
