package com.handicraft.core.dto.Furnitures;

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

@Entity(name = "FurnitureToComment")
@Table(name = "furniture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FurnitureToComment extends FurnitureAbs implements Serializable {

    private static final long serialVersionUID = 1650340433000265294L;

    @JsonManagedReference
    @OneToMany(mappedBy = "furnitureToComment" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<CommentToUserFurniture> commentList;
}
