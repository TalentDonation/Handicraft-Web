package com.handicraft.core.dto.UserEvent;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.dto.Events.EventToUser;
import com.handicraft.core.dto.Users.UserToEvent;
import com.handicraft.core.id.UserEventId;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "UserEvent")
@Table(name = "user_event")
@Data
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEvent implements Serializable{

    private static final long serialVersionUID = -6457669738287574431L;

    @EmbeddedId
    private UserEventId userEventId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid" , insertable = false ,updatable = false)
    private UserToEvent userToEventList;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "eid" , insertable = false ,updatable = false)
    private EventToUser eventToUserList;
}
