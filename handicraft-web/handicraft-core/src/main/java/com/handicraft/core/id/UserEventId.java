package com.handicraft.core.id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserEventId implements Serializable{

    private static final long serialVersionUID = -1538584141886638013L;

    private long uid;

    private long eid;
}
