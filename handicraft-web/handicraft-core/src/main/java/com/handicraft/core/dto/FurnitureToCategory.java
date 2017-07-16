package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-15.
 */

@Entity(name = "FurnitureToCategory")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToCategory implements Serializable{

    private static final long serialVersionUID = 5649056397978792148L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "f_id")
    @NonNull
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
    @JoinColumn(name = "t_id")
    private List<Integer> tidList;
}
