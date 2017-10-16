package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 고승빈 on 2017-08-03.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
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

    protected String sellerPhone;

    protected String sellerKakao;

    protected String price;

    protected String periodOfUse;

    protected String brand;

    protected boolean closed;

    @LastModifiedDate
    protected Date updateAt;

    @CreatedDate
    protected Date createAt;





}
