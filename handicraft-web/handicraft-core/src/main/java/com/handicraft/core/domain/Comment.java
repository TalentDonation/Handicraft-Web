package com.handicraft.core.domain;

import com.handicraft.core.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment", indexes = {
        @Index(name = "comment_idx_title", columnList = "title")
})
public class Comment implements Serializable {
    private static final long serialVersionUID = -2535545684434235058L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cid", nullable = false)
    private long cid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "update_at", nullable = false)
    private ZonedDateTime updateAt;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fid")
    private Furniture furniture;

    public Comment() {
    }

    public Comment(CommentDto commentDto) {
        this.cid = commentDto.getCid();
        this.title = commentDto.getTitle();
        this.contents = commentDto.getContents();
        this.updateAt = commentDto.getUpdateAt();
        this.createAt = commentDto.getCreateAt();
    }
}
