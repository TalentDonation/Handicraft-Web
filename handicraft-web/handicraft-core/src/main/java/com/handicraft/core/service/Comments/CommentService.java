package com.handicraft.core.service.Comments;


import com.handicraft.core.dto.Comments.Comment;
import com.handicraft.core.dto.Comments.CommentToUserFurniture;
import com.handicraft.core.dto.Furnitures.FurnitureToComment;
import com.handicraft.core.dto.Users.UserToComment;
import com.handicraft.core.repository.Comments.CommentRepository;
import com.handicraft.core.repository.Furnitures.FurnitureToCommentRepository;
import com.handicraft.core.repository.Users.UserToCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserToCommentRepository userToCommentRepository;

    @Autowired
    FurnitureToCommentRepository furnitureToCommentRepository;

    public CommentToUserFurniture insert(long uid , long fid , Comment comment)
    {
        UserToComment userToComment = userToCommentRepository.findOne(uid);
        FurnitureToComment furnitureToComment = furnitureToCommentRepository.findOne(fid);

        if(userToComment == null || furnitureToComment == null) return null;

        CommentToUserFurniture commentToUserFurniture = new CommentToUserFurniture();
        commentToUserFurniture.setCid(0);
        commentToUserFurniture.setContents(comment.getContents());
        commentToUserFurniture.setTitle(comment.getTitle());
        commentToUserFurniture.setUpdateAt(null);
        commentToUserFurniture.setCreateAt(null);
        commentToUserFurniture.setFurnitureToComment(furnitureToComment);
        commentToUserFurniture.setUserToComment(userToComment);

        return commentRepository.save(commentToUserFurniture);
    }

    @Transactional
    public void deleteByFid(long fid)
    {
        commentRepository.deleteAllByFurnitureToComment_Fid(fid);
    }
}
