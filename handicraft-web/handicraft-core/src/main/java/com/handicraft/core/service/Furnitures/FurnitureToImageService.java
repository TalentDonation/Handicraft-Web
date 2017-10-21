package com.handicraft.core.service.Furnitures;

import com.handicraft.core.dto.Furnitures.FurnitureToImage;
import com.handicraft.core.repository.Furnitures.FurnitureToImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 고승빈 on 2017-08-06.
 */
@Service
public class FurnitureToImageService {

    @Autowired
    FurnitureToImageRepository furnitureToImageRepository;

    public Page<FurnitureToImage> findFurniturToImagePerPage(PageRequest pageRequest) {
        return furnitureToImageRepository.findAll(pageRequest);
    }

    public FurnitureToImage insertFurnitureToImageByFid(FurnitureToImage furnitureToImage)
    {
        return furnitureToImageRepository.save(furnitureToImage);
    }

    public FurnitureToImage updateFurnitureToImageByFid(FurnitureToImage furnitureToImage) {

        if(!furnitureToImageRepository.exists(furnitureToImage.getFid())) return null;

        return furnitureToImageRepository.save(furnitureToImage);
    }

    public Boolean deleteById(long fid) {
        if(!furnitureToImageRepository.exists(fid)) return false;

        furnitureToImageRepository.delete(fid);
        return true;
    }

    public FurnitureToImage findById(long fid) {
        return furnitureToImageRepository.findOne(fid);
    }

    public List<FurnitureToImage> find() {
        return furnitureToImageRepository.findAll();
    }

    public void delete()
    {
        furnitureToImageRepository.deleteAll();
    }
}
