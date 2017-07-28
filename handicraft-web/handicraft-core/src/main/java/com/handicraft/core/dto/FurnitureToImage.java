package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 고승빈 on 2017-07-26.
 */

@Entity(name = "FurnitureToImage")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToImage implements Serializable {

    private static final long serialVersionUID = 7105682100267586260L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "f_id")
    private int fid;

    private double width;

    private double length;

    private double height;

    private String title;

    private String description;

    private int grade;

    private String manufactureAt;

    private String createAt;

    @ManyToOne
    @JoinColumn(name = "g_id")
    private Image image;
}
