package com.handicraft.core.service;

import com.handicraft.core.dao.FurnitureDao;
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
    FurnitureDao furnitureDao;

    @Override
    public Furniture getByFurniture(int f_id) {
        return furnitureDao.findOne(f_id);
    }

    @Override
    public List<Furniture> getByFurnitureAll() {
        return furnitureDao.findAll();
    }

    @Override
    public Furniture insertToFurniture(Furniture furniture) {

        if(furnitureDao.exists(furniture.getFid()))
        {
            furniture.setFid((int)furnitureDao.count()+1);
        }

        return furnitureDao.save(furniture);
    }

    @Override
    public Furniture updateToFurniture(Furniture furniture) {

        if(!furnitureDao.exists(furniture.getFid())) return null;

        return furnitureDao.save(furniture);
    }
}
