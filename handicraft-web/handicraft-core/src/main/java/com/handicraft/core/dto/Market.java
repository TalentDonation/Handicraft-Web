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
 * Created by 고승빈 on 2017-07-06.
 */

@Entity(name="Market")
@Table(name="market")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Market implements Serializable{

    private static final long serialVersionUID = 2611468829045604962L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "m_id")
    private long mid;

    private boolean admin;

    private boolean share;

    private boolean sold;

    private int price;

    private String info;

    private String location;

    private String lat;

    private String lon;

    private String registerAt;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "f_id")
    private Furniture furniture;


}
