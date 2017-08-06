package com.handicraft.core.id;

import com.handicraft.core.dto.Category;
import com.handicraft.core.dto.Furniture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 고승빈 on 2017-07-27.
 */

@Data
@Embeddable
public class FurnitureCategoryId implements Serializable {

    private static final long serialVersionUID = -2974930552320388715L;


    @Column(name="f_id" , nullable = false )
    private long f_id;


    @Column(name="t_id" , nullable = false )
    private long t_id;



    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
