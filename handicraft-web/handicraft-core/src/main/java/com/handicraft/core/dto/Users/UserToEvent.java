package com.handicraft.core.dto.Users;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handicraft.core.dto.Events.Event;
import com.handicraft.core.dto.Events.EventToUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "UserToEvent")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToEvent extends UserAbs implements Serializable {


    private static final long serialVersionUID = -3675134326772648025L;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_event"
            ,joinColumns = {@JoinColumn(name = "uid")}
            ,inverseJoinColumns = {@JoinColumn(name = "eid")})
    List<Event> eventList;
}
