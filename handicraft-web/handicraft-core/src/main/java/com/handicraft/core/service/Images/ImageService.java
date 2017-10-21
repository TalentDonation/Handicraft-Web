package com.handicraft.core.service.Images;

import com.handicraft.core.dto.Images.Image;
import com.handicraft.core.repository.Images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public String uploadImageS3(MultipartFile multipartFile)
    {
        return null;
    }

    public Boolean removeImageS3(String path)
    {
        return null;

    }

    public String updateImageS3(String path , MultipartFile multipartFile)
    {
        return null;

    }
}
