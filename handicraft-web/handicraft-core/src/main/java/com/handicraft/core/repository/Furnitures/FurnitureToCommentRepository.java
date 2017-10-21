package com.handicraft.core.repository.Furnitures;

import com.handicraft.core.dto.Furnitures.FurnitureToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnitureToCommentRepository extends JpaRepository<FurnitureToComment , Long>{
}
