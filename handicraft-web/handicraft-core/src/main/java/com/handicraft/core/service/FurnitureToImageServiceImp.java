package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToImage;
import com.handicraft.core.repository.FurnitureToImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by 고승빈 on 2017-08-06.
 */
@Service
public class FurnitureToImageServiceImp implements FurnitureToImageService{

    @Autowired
    FurnitureToImageRepository furnitureToImageRepository;

    @Override
    public Page<FurnitureToImage> findFurniturePerPage(PageRequest pageRequest) {
        return furnitureToImageRepository.findAll(pageRequest);
    }

    @Override
    public FurnitureToImage insertFurnitureToImage(FurnitureToImage furnitureToImage)
    {
        return furnitureToImageRepository.save(furnitureToImage);
    }
}
