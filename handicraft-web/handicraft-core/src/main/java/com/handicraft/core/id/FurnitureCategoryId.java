package com.handicraft.core.id;

import com.handicraft.core.dto.Category;
import com.handicraft.core.dto.Furniture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by 고승빈 on 2017-07-27.
 */
public class FurnitureCategoryId implements Serializable {

    private static final long serialVersionUID = -2974930552320388715L;

    private int fid;

    private int tid;

    public FurnitureCategoryId() {
    }

    public FurnitureCategoryId(int fid, int tid) {
        this.fid = fid;
        this.tid = tid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
