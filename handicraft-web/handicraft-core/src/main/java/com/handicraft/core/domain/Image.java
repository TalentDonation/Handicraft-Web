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
@Table(name = "image", indexes = {
        @Index(name = "image_idx_name", columnList = "name", unique = true)
})
@EntityListeners(value = {AuditingEntityListener.class})
public class Image implements Serializable {
    private static final long serialVersionUID = 2811774758180455543L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid", nullable = false)
    private long mid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Furniture furniture;
}
