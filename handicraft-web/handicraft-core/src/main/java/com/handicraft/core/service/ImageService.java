package com.handicraft.core.service;

import com.handicraft.core.domain.Furniture;
import com.handicraft.core.domain.Image;
import com.handicraft.core.dto.ImageDto;
import com.handicraft.core.repository.FurnitureRepository;
import com.handicraft.core.repository.ImageRepository;
import com.handicraft.core.support.AwsModule;
import com.handicraft.core.support.HashUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 고승빈 on 2017-07-26.
 */
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final FurnitureRepository furnitureRepository;
    private final AwsModule awsModule;

    @Autowired
    public ImageService(ImageRepository imageRepository, FurnitureRepository furnitureRepository, AwsModule awsModule) {
        this.imageRepository = imageRepository;
        this.furnitureRepository = furnitureRepository;
        this.awsModule = awsModule;
    }

    @Transactional
    public long upload(long fid, MultipartFile multipartFile) {
        Furniture furniture = furnitureRepository.findOne(fid);
        if (multipartFile == null || furniture == null) return 0L;

        Long mid = imageRepository.count() + 1;
        String extension = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".").toLowerCase();
        String hash = HashUtil.encrypt(mid, "SHA-256");
        long fileSize = 0L;
        try {
            fileSize = awsModule.upload(furniture.getFid(), hash + "." + extension, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = new Image();
        image.setMid(mid);
        image.setExtension(extension);
        image.setFurniture(furniture);
        image.setName(hash);
        image.setCreateAt(ZonedDateTime.now());
        image.setUpdateAt(ZonedDateTime.now());
        imageRepository.saveAndFlush(image);
        return fileSize;
    }

    @Transactional
    public long change(long fid, long mid, MultipartFile multipartFile) {
        Image image = imageRepository.findOne(mid);
        if (multipartFile == null || image == null || image.getFurniture() == null) return 0L;

        String extension = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".").toLowerCase();
        String hash = HashUtil.encrypt(mid, "SHA-256");
        long fileSize = 0L;
        try {
            fileSize = awsModule.change(fid, image.getName() + "." + image.getExtension(), hash + "." + extension, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        image.setUpdateAt(ZonedDateTime.now());
        image.setName(hash);
        image.setExtension(extension);
        image.setFileSize(fileSize);
        imageRepository.saveAndFlush(image);
        return fileSize;
    }

    public List<ImageDto> findByFid(long fid) {
        List<Image> images = imageRepository.findAllByFurnitureFid(fid);
        return images.stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
    }

    public ImageDto findOne(long mid) {
        Image image = imageRepository.findOne(mid);
        if (image == null) {
            throw new IllegalArgumentException();
        }

        return new ImageDto(image);
    }

    public Image findOneByFileName(String fileName) {
        return imageRepository.findByName(fileName);
    }

    @Transactional(rollbackFor = IOException.class)
    public void removeAllByFid(long fid) {
        Furniture furniture = furnitureRepository.findOne(fid);
        if (furniture == null || furniture.getImages() == null) {
            throw new IllegalArgumentException();
        }

        List<Image> images = furniture.getImages();
        images.forEach(image -> {
            awsModule.remove(fid, image.getName() + "." + image.getExtension());
        });

        imageRepository.removeAllByFurnitureFid(fid);
    }

    @Transactional(rollbackFor = IOException.class)
    public void remove(long mid) {
        Image image = imageRepository.findOne(mid);
        if (image == null) {
            throw new IllegalArgumentException();
        }

        awsModule.remove(image.getFurniture().getFid(), image.getName() + "." + image.getExtension());
        imageRepository.delete(mid);
    }
}
