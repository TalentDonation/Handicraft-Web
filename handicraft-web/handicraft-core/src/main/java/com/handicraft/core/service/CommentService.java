package com.handicraft.core.service;


import com.handicraft.core.domain.Comment;
import com.handicraft.core.domain.Furniture;
import com.handicraft.core.domain.User;
import com.handicraft.core.dto.CommentDto;
import com.handicraft.core.repository.CommentRepository;
import com.handicraft.core.repository.FurnitureRepository;
import com.handicraft.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final FurnitureRepository furnitureRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, FurnitureRepository furnitureRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.furnitureRepository = furnitureRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDto> findByUid(long uid) {
        List<Comment> comments = commentRepository.findByUserUid(uid);
        return comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public List<CommentDto> findByFid(long fid) {
        List<Comment> comments = commentRepository.findByFurnitureFid(fid);
        return comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public CommentDto findByCid(long cid) {
        Comment comment = commentRepository.findOne(cid);
        return new CommentDto(comment);
    }

    public List<CommentDto> find(int page, int perPage) {
        PageRequest pageRequest = new PageRequest(page, perPage);
        Page<Comment> comments = commentRepository.findAll(pageRequest);
        Iterator<Comment> iterator = comments.iterator();
        ArrayList<CommentDto> commentDtos = new ArrayList<>();
        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            CommentDto commentDto = new CommentDto(comment);
            commentDto.setFid(comment.getFurniture().getFid());
            commentDto.setFid(comment.getUser().getUid());
            commentDtos.add(commentDto);
        }

        return commentDtos;
    }

    public void create(CommentDto commentDto) {
        Furniture furniture = furnitureRepository.findOne(commentDto.getFid());
        User user = userRepository.findOne(commentDto.getUid());
        if (furniture == null || user == null) {
            throw new IllegalArgumentException();
        }

        Comment comment = new Comment(commentDto);
        comment.setUser(user);
        comment.setFurniture(furniture);
        commentRepository.saveAndFlush(comment);
    }

    public void update(List<CommentDto> commentDtos) {
        boolean isAnyNotExist = commentDtos.stream().anyMatch(object -> !commentRepository.exists(object.getCid()));
        if (isAnyNotExist) {
            throw new IllegalArgumentException();
        }

        List<Comment> comments = commentDtos.stream().map(object -> {
            Comment comment = commentRepository.findOne(object.getCid());
            comment.setContents(object.getContents());
            comment.setTitle(object.getTitle());
            comment.setUpdateAt(object.getUpdateAt());
            return comment;
        }).collect(Collectors.toList());

        commentRepository.save(comments);
        commentRepository.flush();
    }

    public void updateOne(CommentDto commentDto) {
        Comment comment = commentRepository.findOne(commentDto.getCid());
        if (comment == null) {
            throw new IllegalArgumentException();
        }

        comment.setContents(commentDto.getContents());
        comment.setTitle(commentDto.getTitle());
        comment.setUpdateAt(commentDto.getUpdateAt());
        commentRepository.saveAndFlush(comment);
    }

    public void removeAll() {
        commentRepository.deleteAll();
    }

    public void removeByCid(long cid) {
        commentRepository.delete(cid);
    }

    public void removeByUid(long uid) {
        commentRepository.removeAllByUserUid(uid);
    }

    public void removeByFid(long fid) {
        commentRepository.removeAllByFurnitureFid(fid);
    }
}
