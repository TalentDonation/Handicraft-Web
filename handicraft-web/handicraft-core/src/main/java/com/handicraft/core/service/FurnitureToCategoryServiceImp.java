package com.handicraft.core.service;

import com.handicraft.core.dao.FurnitureToCategoryDao;
import com.handicraft.core.dto.FurnitureToCategory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 고승빈 on 2017-07-15.
 */
public class FurnitureToCategoryServiceImp implements FurnitureToCategoryService {

    @Autowired
    FurnitureToCategoryDao furnitureToCategoryDao;


    @Override
    public FurnitureToCategory insertToFurnitureToCategory(FurnitureToCategory furnitureToCategory) {

        return null;
    }
}
