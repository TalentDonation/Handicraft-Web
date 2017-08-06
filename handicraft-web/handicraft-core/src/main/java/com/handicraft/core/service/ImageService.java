package com.handicraft.core.service;

import com.handicraft.core.dto.Image;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-26.
 */
public interface ImageService {

//    List<Image> findImagesByFid(long fid);

    Image insertImages(Image image);

    List<Image> updateImages(List<Image> imageList);

//    List<Image> deleteImages(long fid);
}
