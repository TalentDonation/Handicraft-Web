package com.handicraft.core.repository;

import com.handicraft.core.dto.FurnitureToImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-26.
 */
@Repository
public interface FurnitureToImageRepository extends JpaRepository<FurnitureToImage, Integer> {
}
