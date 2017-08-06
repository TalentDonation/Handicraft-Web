package com.handicraft.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 고승빈 on 2017-08-03.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class FurnitureAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id" , nullable = false)
    protected long fid;

    protected double width;

    protected double length;

    protected double height;

    protected String title;

    protected String description;

    protected String brand;

    protected String grade;

    protected String state;

    protected String location;

    protected String type;

    protected double lat;

    protected double lon;

    protected int periodOfUse;

    protected String createAt;

}
