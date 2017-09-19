package com.handicraft.core.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class UserAbs {

    @Id
    @Column(nullable = false)
    protected long uid;

    protected String name;

    protected String nickname;

    protected String phone;

    protected String address;

    protected String birthday;

    @CreatedDate
    protected Date joinAt;

}
