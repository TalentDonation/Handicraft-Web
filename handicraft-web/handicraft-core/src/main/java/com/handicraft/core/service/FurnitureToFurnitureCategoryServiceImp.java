package com.handicraft.core.service;

import com.handicraft.core.dto.FurnitureToFurnitureCategory;
import com.handicraft.core.repository.FurnitureToFurnitureCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-28.
 */
@Service
public class FurnitureToFurnitureCategoryServiceImp implements FurnitureToFurnitureCategoryService {

    @Autowired
    FurnitureToFurnitureCategoryRepository furnitureToFurnitureCategoryRepository;

    @Override
    public FurnitureToFurnitureCategory getByFurniture(int f_id) {
        return furnitureToFurnitureCategoryRepository.findOne(f_id);
    }

    @Override
    public List<FurnitureToFurnitureCategory> getByFurnitureAll() {
        return  furnitureToFurnitureCategoryRepository.findAll();
    }

    @Override
    public Page<FurnitureToFurnitureCategory> getByFurniturePerPage(PageRequest pageRequest) {
        return furnitureToFurnitureCategoryRepository.findAll(pageRequest);
    }
}
