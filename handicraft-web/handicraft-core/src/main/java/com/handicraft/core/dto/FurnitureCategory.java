package com.handicraft.core.dto;

import com.handicraft.core.id.FurnitureCategoryId;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 고승빈 on 2017-07-15.
 */

@Entity(name = "FurnitureCategory")
@Table(name = "furniture_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureCategory implements Serializable {

    private static final long serialVersionUID = 6135259929286807612L;

    @Id
    @GeneratedValue
    @Column(name = "ft_id" , nullable = false)
    private long ftid;


    @Column(name = "t_id" , nullable = false)
    private long tid;

//    @EmbeddedId
//    private FurnitureCategoryId furnitureCategoryId;

}
