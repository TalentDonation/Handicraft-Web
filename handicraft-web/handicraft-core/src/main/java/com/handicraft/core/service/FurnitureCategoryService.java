package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureCategory;
import com.handicraft.core.id.FurnitureCategoryId;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-15.
 */
public interface FurnitureCategoryService {

    List<FurnitureCategory> insertToFurnitureCategory(List<FurnitureCategory> furnitureCategoryList);
}
