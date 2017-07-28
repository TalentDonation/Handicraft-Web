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
    @Column(name = "g_id")
    private int gid;

    private String uri;

    private String extension;

    private String registerAt;

}
