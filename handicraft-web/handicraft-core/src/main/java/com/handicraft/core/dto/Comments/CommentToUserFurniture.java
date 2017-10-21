package com.handicraft.core.dto.Comments;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handicraft.core.dto.Furnitures.FurnitureToComment;
import com.handicraft.core.dto.Users.UserToComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "CommentToUserFurniture")
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentToUserFurniture extends CommentAbs implements Serializable {

    private static final long serialVersionUID = -8297575072300635337L;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "fid")
    FurnitureToComment furnitureToComment;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "uid")
    UserToComment userToComment;

}
