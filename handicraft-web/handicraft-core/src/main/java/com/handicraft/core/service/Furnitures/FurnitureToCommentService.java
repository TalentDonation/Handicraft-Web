package com.handicraft.core.service.Furnitures;

import com.handicraft.core.dto.Furnitures.FurnitureToComment;
import com.handicraft.core.repository.Furnitures.FurnitureToCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FurnitureToCommentService {

    @Autowired
    FurnitureToCommentRepository furnitureToCommentRepository;


    public FurnitureToComment find(long fid)
    {
        return furnitureToCommentRepository.findOne(fid);
    }
}
