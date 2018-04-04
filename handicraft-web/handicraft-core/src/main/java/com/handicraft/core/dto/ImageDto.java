package com.handicraft.core.dto;

import com.handicraft.core.domain.Image;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class ImageDto implements Serializable {
    private static final long serialVersionUID = -2131067366978925213L;

    private long mid;
    private String name;
    private String extension;
    private long fileSize;
    private ZonedDateTime updateAt;
    private ZonedDateTime createAt;
    private long fid;

    public ImageDto() {
    }

    public ImageDto(Image image) {
        this.mid = image.getMid();
        this.name = image.getName();
        this.extension = image.getExtension();
        this.updateAt = image.getUpdateAt();
        this.createAt = image.getCreateAt();
    }
}
