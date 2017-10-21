package com.handicraft.core.service.Furnitures;

import com.handicraft.core.dto.Furnitures.Furniture;
import com.handicraft.core.dto.Furnitures.FurnitureToImage;
import com.handicraft.core.repository.Furnitures.FurnitureRepository;
import com.handicraft.core.repository.Furnitures.FurnitureToImageRepository;
import com.handicraft.core.repository.Images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Service
public class FurnitureService {

    @Autowired
    FurnitureRepository furnitureRepository;

    @Autowired
    FurnitureToImageRepository furnitureToImageRepository;

    @Autowired
    ImageRepository imageRepository;

    public Page<FurnitureToImage> findFurniturePerPage(PageRequest pageRequest) {
        return furnitureToImageRepository.findAll(pageRequest);
    }

    public Furniture findFurnitureByFid(long f_id) {
        return furnitureRepository.findOne(f_id);
    }

    @Transactional
    public void insertFurnitureByFid(Furniture furniture) {

        furnitureRepository.saveAndFlush(furniture);
    }

    public Furniture updateFurnitureByFid(Furniture furniture) {

        if(!furnitureRepository.exists(furniture.getFid())) return null;

        return furnitureRepository.save(furniture);
    }

    public Boolean deleteFurnitureByFid(long f_id) {

        if(!furnitureRepository.exists(f_id)) return false;

        furnitureRepository.delete(f_id);
        return true;
    }

    public List<Furniture> updateFurnitureList(List<Furniture> furnitureList) {

        for(Furniture furniture : furnitureList)
        {
            if(!furnitureRepository.exists(furniture.getFid())) return null;
        }

        return furnitureRepository.save(furnitureList);
    }

    public void deleteFurnitureList() {

        furnitureRepository.deleteAll();;
    }

    public void deleteImagesByFid(long fid) {

        Furniture furniture = furnitureRepository.findOne(fid);

//        imageRepository.delete(furniture.getImages());

    }

    public Furniture findLastFurnitureByFid() {
        return furnitureRepository.findTopByOrderByFidDesc();
    }
}
