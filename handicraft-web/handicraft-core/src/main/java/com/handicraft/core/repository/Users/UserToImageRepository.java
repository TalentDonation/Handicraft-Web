package com.handicraft.core.repository.Users;

import com.handicraft.core.dto.Users.UserToImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToImageRepository extends JpaRepository<UserToImage , Long>{
}