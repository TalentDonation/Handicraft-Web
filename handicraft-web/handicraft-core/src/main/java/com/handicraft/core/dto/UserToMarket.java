package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 고승빈 on 2017-07-07.
 */

@Entity(name = "MarketToUser")
@Table(name="market")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToMarket implements Serializable {

    private static final long serialVersionUID = -706897138652499801L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "m_id")
    protected long mid;

    protected boolean admin;

    protected boolean share;

    protected boolean sold;

    protected int price;

    protected String info;

    protected String location;

    protected String lat;

    protected String lon;

    protected String registerAt;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;


}
