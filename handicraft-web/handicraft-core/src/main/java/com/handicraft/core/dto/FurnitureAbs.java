package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handicraft.core.utils.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    protected double lat;

    protected double lon;

    @Enumerated(EnumType.STRING)
    protected Type type;

    protected boolean sold;

    protected int price;

    protected String info;

    protected int periodOfUse;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime purchaseAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime updateAt;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected LocalDateTime createAt;

    @Column(name = "u_id")
    protected int uid;
}
