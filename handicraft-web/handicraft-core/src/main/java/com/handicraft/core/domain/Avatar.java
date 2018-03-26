package com.handicraft.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class Avatar implements Serializable {
    private static final long serialVersionUID = 5204160773524849886L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gid", nullable = false)
    private long gid;

    @Column(name = "name", nullable = false)
    private String name;
    private long size;
    private String extension;
    private ZonedDateTime updateAt;
    private ZonedDateTime createAt;
}
