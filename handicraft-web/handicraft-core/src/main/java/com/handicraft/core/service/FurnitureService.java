package com.handicraft.core.service;

import com.handicraft.core.domain.Furniture;
import com.handicraft.core.domain.Image;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.FurnitureDto;
import com.handicraft.core.repository.FurnitureRepository;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.support.AwsModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Service
public class FurnitureService {
    private final FurnitureRepository furnitureRepository;
    private final UserRepository userRepository;
    private final AwsModule awsModule;

    @Autowired
    public FurnitureService(FurnitureRepository furnitureRepository, UserRepository userRepository, AwsModule awsModule) {
        this.furnitureRepository = furnitureRepository;
        this.userRepository = userRepository;
        this.awsModule = awsModule;
    }

    public List<FurnitureDto> find(long uid, int page, int perPage) {
        Pageable pageable = new PageRequest(page, perPage, Sort.Direction.ASC, "fid");
        Page<Furniture> furniture = furnitureRepository.findAllByUserUid(uid, pageable);
        return furniture
                .map(FurnitureDto::new)
                .getContent();
    }

    public List<FurnitureDto> find(int page, int perPage) {
        Pageable pageable = new PageRequest(page, perPage, Sort.Direction.ASC, "fid");
        Page<Furniture> furniture = furnitureRepository.findAll(pageable);
        return furniture
                .map(FurnitureDto::new)
                .getContent();
    }

    public FurnitureDto findOne(long fid) {
        Furniture furniture = furnitureRepository.findOne(fid);
        return new FurnitureDto(furniture);
    }

    public void create(FurnitureDto furnitureDto) {
        User user = userRepository.findOne(furnitureDto.getUid());
        if (user == null) {
            throw new IllegalArgumentException();
        }

        Furniture furniture = new Furniture(furnitureDto);
        furniture.setUser(user);
        furnitureRepository.save(furniture);
    }

    public void update(List<FurnitureDto> furnitureDtos) {
        List<Furniture> furnitureList = new ArrayList<>();
        furnitureDtos.forEach(object -> {
            Furniture furniture = new Furniture(object);
            if (!furnitureRepository.exists(furniture.getFid())) {
                throw new IllegalArgumentException();
            }

            furnitureList.add(furniture);
        });

        furnitureRepository.save(furnitureList);
    }

    public void updateOne(FurnitureDto furnitureDto) {
        Furniture furniture = new Furniture(furnitureDto);
        furnitureRepository.save(furniture);
    }

    @Transactional
    public void removeByUid(long uid) {
        User user = userRepository.findOne(uid);
        List<Furniture> furnitureList = user.getFurnitures();
        furnitureList.forEach(furniture -> {
            remove(furniture.getFid());
        });
        furnitureRepository.delete(furnitureList);
    }

    @Transactional(rollbackFor = IOException.class)
    public void remove(long fid) {
        Furniture furniture = furnitureRepository.findOne(fid);
        List<Image> images = furniture.getImages();
        if(images != null) {
            images.forEach(image -> {
                awsModule.remove(fid, image.getName() + "." + image.getExtension());
            });
        }

        furnitureRepository.delete(fid);
    }
}
