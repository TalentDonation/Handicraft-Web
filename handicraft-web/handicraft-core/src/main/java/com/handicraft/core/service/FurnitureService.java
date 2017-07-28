package com.handicraft.core.service;

import com.handicraft.core.dto.Furniture;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */


public interface FurnitureService {
    Furniture getByFurniture(int f_id);

    List<Furniture> getByFurnitureAll();

    Furniture insertToFurniture(Furniture furniture);

    Furniture updateToFurniture(Furniture furniture);

    Boolean deleteToFurniture(int f_id);
}
