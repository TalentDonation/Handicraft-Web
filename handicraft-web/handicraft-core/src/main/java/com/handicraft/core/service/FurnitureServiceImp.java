package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToImage;
import com.handicraft.core.dto.Image;
import com.handicraft.core.id.FurnitureCategoryId;
import com.handicraft.core.repository.FurnitureRepository;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.repository.FurnitureToImageRepository;
import com.handicraft.core.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Service
public class FurnitureServiceImp implements FurnitureService{

    @Autowired
    FurnitureRepository furnitureRepository;

    @Autowired
    FurnitureToImageRepository furnitureToImageRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Page<FurnitureToImage> findFurniturePerPage(PageRequest pageRequest) {
        return furnitureToImageRepository.findAll(pageRequest);
    }

    @Override
    public Furniture findFurnitureByFid(long f_id) {
        return furnitureRepository.findOne(f_id);
    }

    @Override
    @Transactional
    public void insertFurnitureByFid(Furniture furniture) {


//        furniture.setFid(furnitureRepository.findTopByOrderByFidDesc().getFid() + 1);

        furnitureRepository.save(furniture);
    }

    @Override
    public Furniture updateFurnitureByFid(Furniture furniture) {

        if(!furnitureRepository.exists(furniture.getFid())) return null;

        return furnitureRepository.save(furniture);
    }

    @Override
    public Boolean deleteFurnitureByFid(long f_id) {

        if(!furnitureRepository.exists(f_id)) return false;

        furnitureRepository.delete(f_id);
        return true;
    }

    @Override
    public List<Furniture> updateFurnitureList(List<Furniture> furnitureList) {

        for(Furniture furniture : furnitureList)
        {
            if(!furnitureRepository.exists(furniture.getFid())) return null;
        }

        return furnitureRepository.save(furnitureList);
    }

    @Override
    public void deleteFurnitureList() {

        furnitureRepository.deleteAll();;
    }

    @Override
    public void deleteImagesByFid(long fid) {

        Furniture furniture = furnitureRepository.findOne(fid);

//        imageRepository.delete(furniture.getImages());

    }
}
