package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToImageToFurnitureCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by 고승빈 on 2017-08-03.
 */
public interface FurnitureToImageToFurnitureCategoryService {

    Page<FurnitureToImageToFurnitureCategory> getByFurniturePerPage(PageRequest pageRequest);

    FurnitureToImageToFurnitureCategory getByFurniture(long f_id);
}
