package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handicraft.core.utils.converter.LocalDateTimeAttributeConverter;
import com.handicraft.core.utils.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by 고승빈 on 2017-08-03.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class FurnitureAbs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected long fid;

    protected String title;

    protected String state;

    protected String grade;

    protected String description;

    protected double width;

    protected double length;

    protected double height;

    protected String location;

    protected String type;

    protected int price;

    protected String info;

    protected String periodOfUse;

    protected String brand;

    protected boolean closed;

//    @LastModifiedDate
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    @Convert(converter = LocalDateTimeAttributeConverter.class)
//    protected LocalDateTime updateAt;
//
//    @CreatedDate
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    @Convert(converter = LocalDateTimeAttributeConverter.class)
//    protected LocalDateTime createAt;


    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date updateAt;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date createAt;



}
