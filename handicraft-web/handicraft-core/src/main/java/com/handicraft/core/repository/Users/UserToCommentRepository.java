package com.handicraft.core.repository.Users;


import com.handicraft.core.dto.Users.UserToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToCommentRepository extends JpaRepository<UserToComment , Long>{
}
