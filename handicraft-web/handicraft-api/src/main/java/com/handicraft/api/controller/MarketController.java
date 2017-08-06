package com.handicraft.api.controller;

import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToImageToFurnitureCategory;
import com.handicraft.core.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@Api(value = "market" , description = "Market API")
public class MarketController {

    @Autowired
    MarketService marketService;


    @GetMapping("/markets")
    @ApiOperation(value = "" , notes = "Show several market by page")
    public Page<FurnitureToImageToFurnitureCategory> findByMarketPerPage(@RequestParam("page") int page , @RequestParam("per_page") int page_page)
    {
        PageRequest pageRequest  = new PageRequest(page , page_page , Sort.Direction.ASC , "fid");

        return null;
    }


//    @PostMapping("/markets")
//    @ApiOperation(value = "" , notes = "Create a new furniture")
//    public ResponseEntity insertToFurniture(@ModelAttribute FurnitureToFurnitureCategory furnitureToFurnitureCategory , @RequestParam("images") List<MultipartFile> multipartFiles )
//    {
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("/markets")
    @ApiOperation(value = "" , notes = "Update one furniture about furniture id")
    public ResponseEntity updateToFurnitureById(@ModelAttribute Furniture furnitureParam)
    {

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/markets")
    @ApiOperation(value = "" , notes = "Delete one furniture about furniture id")
    public ResponseEntity deleteToFurnitureById(@PathVariable("fid") long fid )
    {
        return new ResponseEntity(HttpStatus.OK);
    }

}
