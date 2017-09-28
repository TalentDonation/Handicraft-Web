package com.handicraft.core.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Event")
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event implements Serializable{


    private static final long serialVersionUID = 50324411440539315L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long eid;

    protected String title;

    protected Date start;

    protected Date end;



}
