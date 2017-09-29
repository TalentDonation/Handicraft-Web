package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity(name = "EventToUser")
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventToUser extends EventAbs  implements Serializable{

    private static final long serialVersionUID = 50314411440539315L;

    @ManyToMany(fetch = FetchType.LAZY , mappedBy = "eventList")
    private List<UserToEvent> userToEventList;
}
