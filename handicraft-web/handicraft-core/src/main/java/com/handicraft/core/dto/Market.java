package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    @NonNull
    protected int mid;

    protected boolean admin;

    protected boolean share;

    protected boolean sold;

    protected int price;

    protected String info;

    protected String location;

    protected String lat;

    protected String lon;

    protected String registerAt;

    @Column(name = "u_id")
    private int uid;

    @Column(name = "f_id")
    private int fid;


}
