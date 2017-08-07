package com.handicraft.core.service;

import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by 고승빈 on 2017-08-06.
 */
public interface FurnitureToImageService {

    Page<FurnitureToImage> findFurniturToImagePerPage(PageRequest pageRequest);

    FurnitureToImage insertFurnitureToImageByFid(FurnitureToImage furnitureToImage);

    FurnitureToImage updateFurnitureToImageByFid(FurnitureToImage furnitureToImage);

    Boolean deleteFurnitureToImageByFid(long f_id);
}
