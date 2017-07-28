package com.handicraft.core.repository;

import com.handicraft.core.dto.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-15.
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {
}
