package com.handicraft.core.repository.Furnitures;

import com.handicraft.core.dto.Furnitures.FurnitureToImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-08-06.
 */

@Repository
public interface FurnitureToImageRepository extends JpaRepository<FurnitureToImage , Long> {
}
