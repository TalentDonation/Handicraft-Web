package com.handicraft.core.service;

import com.handicraft.core.dto.Image;
import com.handicraft.core.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-26.
 */
@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;


    public Image findImageByGid(long gid) {
        return imageRepository.findOne(gid);
    }

    public Image findImageByLastIndex() {
        return imageRepository.findTopByOrderByGidDesc();
    }

    public Image insertImages(Image image) {

        if(imageRepository.exists(image.getGid()))
        {
            image.setGid(imageRepository.findTopByOrderByGidDesc().getGid() + 1);
        }
        return imageRepository.save(image);
    }

    public List<Image> updateImages(List<Image> imageList) {
        return imageRepository.save(imageList);
    }

    public Image updateImagesByGid(Image image) {
        return imageRepository.save(image);
    }
}
