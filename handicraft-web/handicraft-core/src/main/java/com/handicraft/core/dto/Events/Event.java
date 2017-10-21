package com.handicraft.core.dto.Events;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Event")
@Table(name = "event")
@Data
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event extends EventAbs implements  Serializable{

    private static final long serialVersionUID = 10314411440539315L;

    public Event() {
    }

    public Event(long eid, String title,String color, Date start, Date end) {
        super(eid, title,color, start, end);
    }
}
