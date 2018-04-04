package com.handicraft.core.dto;

import com.handicraft.core.domain.Event;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class EventDto implements Serializable {
    private static final long serialVersionUID = 6364925294332401860L;

    private long eid;
    private String title;
    private String color;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private long uid;

    public EventDto() {
    }

    public EventDto(Event event) {
        this.eid = event.getEid();
        this.title = event.getTitle();
        this.color = event.getColor();
        this.start = event.getStart();
        this.end = event.getEnd();
    }
}
