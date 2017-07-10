package com.handicraft.core.dao;

import com.handicraft.core.dto.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Repository
public interface MarketDao extends JpaRepository<Market, Integer>  {
}
