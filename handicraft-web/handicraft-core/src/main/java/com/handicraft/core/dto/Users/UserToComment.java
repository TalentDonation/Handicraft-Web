package com.handicraft.core.dto.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.handicraft.core.dto.Comments.CommentToUserFurniture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity(name = "UserToComment")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToComment extends UserAbs implements Serializable{


    private static final long serialVersionUID = 172810051328550918L;

    @JsonManagedReference
    @OneToMany(mappedBy = "userToComment" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<CommentToUserFurniture> commentList;
}
