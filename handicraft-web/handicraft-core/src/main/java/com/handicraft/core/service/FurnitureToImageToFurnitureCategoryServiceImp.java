package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToImageToFurnitureCategory;
import com.handicraft.core.repository.FurnitureToImageToFurnitureCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by 고승빈 on 2017-08-03.
 */
@Service
public class FurnitureToImageToFurnitureCategoryServiceImp implements FurnitureToImageToFurnitureCategoryService {

    @Autowired
    FurnitureToImageToFurnitureCategoryRepository furnitureToImageToFurnitureCategoryRepository;

    @Override
    public Page<FurnitureToImageToFurnitureCategory> getByFurniturePerPage(PageRequest pageRequest) {
        return furnitureToImageToFurnitureCategoryRepository.findAll(pageRequest);
    }

    @Override
    public FurnitureToImageToFurnitureCategory getByFurniture(long f_id) {
        return furnitureToImageToFurnitureCategoryRepository.findOne(f_id);
    }
}
