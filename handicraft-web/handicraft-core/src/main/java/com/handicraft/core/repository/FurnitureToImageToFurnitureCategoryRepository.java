package com.handicraft.core.repository;

import com.handicraft.core.dto.FurnitureToImageToFurnitureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-08-03.
 */
@Repository
public interface FurnitureToImageToFurnitureCategoryRepository extends JpaRepository<FurnitureToImageToFurnitureCategory,Long> {
}
