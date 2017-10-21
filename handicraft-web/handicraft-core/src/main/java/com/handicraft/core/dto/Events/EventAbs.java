package com.handicraft.core.dto.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class EventAbs implements Serializable {

    private static final long serialVersionUID = 50324411440539315L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long eid;

    protected String title;

    protected String color;

    protected Date start;

    protected Date end;
}
