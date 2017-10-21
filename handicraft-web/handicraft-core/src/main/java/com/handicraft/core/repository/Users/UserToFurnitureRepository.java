package com.handicraft.core.repository.Users;

import com.handicraft.core.dto.Users.UserToFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToFurnitureRepository extends JpaRepository<UserToFurniture , Long>{
}
