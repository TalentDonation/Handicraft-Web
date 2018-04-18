package com.handicraft.core.repository;

import com.handicraft.core.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserUid(long uid);

    List<Comment> findByFurnitureFid(long fid);

    void removeAllByUserUid(long uid);

    void removeAllByFurnitureFid(long fid);
}
