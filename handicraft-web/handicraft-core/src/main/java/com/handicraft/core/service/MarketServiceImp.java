package com.handicraft.core.service;

import com.handicraft.core.dao.MarketDao;
import com.handicraft.core.dao.MarketToFurnitureDao;
import com.handicraft.core.dao.MarketToUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@Service
public class MarketServiceImp implements MarketService{

    @Autowired
    MarketDao marketDao;

    @Autowired
    MarketToUserDao marketToUserDao;

    @Autowired
    MarketToFurnitureDao marketToFurnitureDao;


}
