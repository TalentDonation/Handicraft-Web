package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@Entity(name = "Image")
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(value = { AuditingEntityListener.class })
public class Image implements Serializable {


    private static final long serialVersionUID = 294219108849226406L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid" , nullable = false)
    private long gid;

    private String path;

    @Column(name="name" , nullable = false)
    private String name;

    private String extension;


    @LastModifiedDate
    private Date updateAt;

    @CreatedDate
    private Date createAt;



}
