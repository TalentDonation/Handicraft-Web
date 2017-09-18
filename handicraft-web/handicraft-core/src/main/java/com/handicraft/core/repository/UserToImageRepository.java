package com.handicraft.core.repository;

import com.handicraft.core.dto.UserToImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToImageRepository extends JpaRepository<UserToImage , Long>{
}
