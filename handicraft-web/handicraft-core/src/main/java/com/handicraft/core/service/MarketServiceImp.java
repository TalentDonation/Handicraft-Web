package com.handicraft.core.service;

import com.handicraft.core.dto.Market;
import com.handicraft.core.repository.MarketRepository;
import com.handicraft.core.repository.FurnitureToMarketRepository;
import com.handicraft.core.repository.UserToMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@Service
public class MarketServiceImp implements MarketService{

    @Autowired
    MarketRepository marketDao;

    @Override
    public Market findMarketByMid(long mid) {
        return marketDao.findOne(mid);
    }
}
