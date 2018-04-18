package com.handicraft.core.domain;

import com.handicraft.core.dto.EventDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Table(name = "event", indexes = {
        @Index(name = "event_idx_title", columnList = "title")
})
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class Event implements Serializable {
    private static final long serialVersionUID = 3277558191103213355L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eid", nullable = false)
    private long eid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "start", nullable = false)
    private ZonedDateTime start;

    @Column(name = "end", nullable = false)
    private ZonedDateTime end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;

    public Event() {
    }

    public Event(EventDto eventDto) {
        this.eid = eventDto.getEid();
        this.title = eventDto.getTitle();
        this.color = eventDto.getColor();
        this.start = eventDto.getStart();
        this.end = eventDto.getEnd();
    }
}
