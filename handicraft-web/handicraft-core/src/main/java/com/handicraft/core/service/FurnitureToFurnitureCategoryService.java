package com.handicraft.core.service;

import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToFurnitureCategory;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-28.
 */
public interface FurnitureToFurnitureCategoryService {

    FurnitureToFurnitureCategory getByFurniture(int f_id);

    List<FurnitureToFurnitureCategory> getByFurnitureAll();
}
