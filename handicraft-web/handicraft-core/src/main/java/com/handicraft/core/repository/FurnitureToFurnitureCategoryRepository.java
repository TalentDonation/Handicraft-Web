package com.handicraft.core.repository;

import com.handicraft.core.dto.FurnitureToFurnitureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-28.
 */
@Repository
public interface FurnitureToFurnitureCategoryRepository extends JpaRepository<FurnitureToFurnitureCategory, Integer> {
}
