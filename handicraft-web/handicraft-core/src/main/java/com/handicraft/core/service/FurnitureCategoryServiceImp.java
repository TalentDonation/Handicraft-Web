package com.handicraft.core.service;

import com.handicraft.core.id.FurnitureCategoryId;
import com.handicraft.core.repository.FurnitureCategoryRepository;
import com.handicraft.core.dto.FurnitureCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-15.
 */
@Service
public class FurnitureCategoryServiceImp implements FurnitureCategoryService {

    @Autowired
    FurnitureCategoryRepository furnitureCategoryRepository;


    @Override
    public List<FurnitureCategory> insertToFurnitureCategory(List<FurnitureCategory> furnitureCategoryList) {

        return furnitureCategoryRepository.save(furnitureCategoryList);
    }
}
