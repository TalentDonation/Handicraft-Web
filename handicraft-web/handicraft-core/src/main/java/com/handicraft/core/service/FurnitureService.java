package com.handicraft.core.service;

import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */


public interface FurnitureService {

    Page<FurnitureToImage> findFurniturePerPage(PageRequest pageRequest);

    Furniture findFurnitureByFid(long f_id);

    void insertFurnitureByFid(Furniture furniture);

    Furniture updateFurnitureByFid(Furniture furniture);

    Boolean deleteFurnitureByFid(long f_id);

    List<Furniture> updateFurnitureList(List<Furniture> furnitureList);

    void deleteFurnitureList();

    void deleteImagesByFid(long fid);

}
