package com.handicraft.core.repository;

import com.handicraft.core.dto.UserToMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-08.
 */
@Repository
public interface UserToMarketRepository extends JpaRepository<UserToMarket,Long> {
}
