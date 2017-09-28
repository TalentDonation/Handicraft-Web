package com.handicraft.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.id.CommentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "Comment")
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {

    @EmbeddedId
    private CommentId cid;

    private String title;

    private String contents;

    @LastModifiedDate
    protected Date updateAt;

    @CreatedDate
    protected Date createAt;


}
