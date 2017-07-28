package com.handicraft.core.service;

import com.handicraft.core.repository.FurnitureRepository;
import com.handicraft.core.dto.Furniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Service
public class FurnitureServiceImp implements FurnitureService{

    @Autowired
    FurnitureRepository furnitureRepository;

    @Override
    public Furniture getByFurniture(int f_id) {
        return furnitureRepository.findOne(f_id);
    }

    @Override
    public List<Furniture> getByFurnitureAll() {
        return furnitureRepository.findAll();
    }

    @Override
    public Furniture insertToFurniture(Furniture furniture) {

        if(furnitureRepository.exists(furniture.getFid()))  furniture.setFid(furnitureRepository.findTopByOrderByFidDesc().getFid() + 1);

        return furnitureRepository.save(furniture);
    }

    @Override
    public Furniture updateToFurniture(Furniture furniture) {

        if(!furnitureRepository.exists(furniture.getFid())) return null;

        return furnitureRepository.save(furniture);
    }

    @Override
    public Boolean deleteToFurniture(int f_id) {

        if(!furnitureRepository.exists(f_id)) return false;

        furnitureRepository.delete(f_id);
        return true;
    }
}
