package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by 고승빈 on 2017-08-06.
 */
public interface FurnitureToImageService {

    Page<FurnitureToImage> findFurniturePerPage(PageRequest pageRequest);

    FurnitureToImage insertFurnitureToImage(FurnitureToImage furnitureToImage);
}
