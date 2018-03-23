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
public class Image implements Serializable {
    private static final long serialVersionUID = 2811774758180455543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long mid;

    @Column(name = "name", nullable = false)
    private String name;
    private String extension;
    private long fileSize;
    private ZonedDateTime updateAt;
    private ZonedDateTime createAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Furniture furniture;
}
