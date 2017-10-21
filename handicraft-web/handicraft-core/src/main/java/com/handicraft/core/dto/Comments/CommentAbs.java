package com.handicraft.core.dto.Comments;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class CommentAbs implements Serializable{

    private static final long serialVersionUID = 3650883103283263430L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected long cid;

    protected String title;

    protected String contents;

    @LastModifiedDate
    protected Date updateAt;

    @CreatedDate
    protected Date createAt;

}
