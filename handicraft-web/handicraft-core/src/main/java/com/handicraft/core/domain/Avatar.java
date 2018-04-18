package com.handicraft.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Table(name = "avatar", indexes = {
        @Index(name = "avatar_idx_name", columnList = "name", unique = true)
})
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

    @Column(name = "size")
    private long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;
}
