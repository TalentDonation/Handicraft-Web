package com.handicraft.core.service;

import com.handicraft.core.dto.Image;
import com.handicraft.core.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-26.
 */
@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    ImageRepository imageRepository;

//    @Override
//    public List<Image> findImagesByFid(long fid) {
//        return imageRepository.findByFid(fid);
//    }

    @Override
    public Image insertImages(Image image) {

        if(imageRepository.exists(image.getGid()))
        {
            image.setGid(imageRepository.findTopByOrderByGidDesc().getGid() + 1);
        }
        return imageRepository.save(image);
    }

    @Override
    public List<Image> updateImages(List<Image> imageList) {
        return imageRepository.save(imageList);
    }

//    @Override
//    @Transactional
//    public List<Image> deleteImages(long fid) {
//        return imageRepository.deleteByFid(fid);
//    }
}
