package com.handicraft.core.repository;

import com.handicraft.core.dto.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-26.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {

    Image findTopByOrderByGidDesc();

//    List<Image> findByFid(long fid);

//    List<Image> deleteByFid(long fid);
}