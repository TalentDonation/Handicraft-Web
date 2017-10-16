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

    public Image findById(long gid)
    {
        return imageRepository.findOne(gid);
    }

    public void deleteById(long gid)
    {
        imageRepository.delete(gid);
    }

    public void delete(List<Image> imageList)
    {
        imageRepository.delete(imageList);
    }
}
