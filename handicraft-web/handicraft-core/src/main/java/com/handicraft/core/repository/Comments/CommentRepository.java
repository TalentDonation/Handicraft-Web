package com.handicraft.core.repository.Comments;

import com.handicraft.core.dto.Comments.CommentToUserFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentToUserFurniture, Long>{

    void deleteAllByFurnitureToComment_Fid(long fid);


}
