package com.handicraft.core.dto;

import com.handicraft.core.domain.Comment;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 8666599037666225994L;

    private long cid;
    private String title;
    private String contents;
    private ZonedDateTime updateAt;
    private ZonedDateTime createAt;
    private long fid;
    private long uid;

    public CommentDto() {
    }

    public CommentDto(Comment comment) {
        this.cid = comment.getCid();
        this.title = comment.getTitle();
        this.contents = comment.getContents();
        this.updateAt = comment.getUpdateAt();
        this.createAt = comment.getCreateAt();
        this.fid = comment.getFurniture().getFid();
        this.uid = comment.getUser().getUid();
    }
}
