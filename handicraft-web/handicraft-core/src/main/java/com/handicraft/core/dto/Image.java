package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.utils.converter.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@Entity(name = "Image")
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Image implements Serializable {


    private static final long serialVersionUID = 294219108849226406L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid" , nullable = false)
    private long gid;


    @Column(name="name" , nullable = false)
    private String name;

    private String extension;


    @LastModifiedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateAt;

    @CreatedDate
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;



}
