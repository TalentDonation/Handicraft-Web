package com.handicraft.core.service;

import com.handicraft.core.dao.FurnitureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Service
public class FurnitureServiceImp implements FurnitureService{

    @Autowired
    FurnitureDao furnitureDao;


}
