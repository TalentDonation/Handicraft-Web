package com.handicraft.core.repository;


import com.handicraft.core.domain.Furniture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 고승빈 on 2017-07-06.
 */
@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
    Page<Furniture> findAllByUserUid(long uid, Pageable pageable);
}
