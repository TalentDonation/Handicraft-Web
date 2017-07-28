package com.handicraft.api.controller;

import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureCategory;
import com.handicraft.core.dto.FurnitureToFurnitureCategory;
import com.handicraft.core.service.FurnitureCategoryService;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.FurnitureToFurnitureCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@RequestMapping("/furniture")
@Api(value = "/furniture" , description = "Furniture API")
public class FurnitureController {

    @Autowired
    FurnitureService furnitureService;

    @Autowired
    FurnitureCategoryService furnitureCategoryService;

    @Autowired
    FurnitureToFurnitureCategoryService furnitureToFurnitureCategoryService;

    @GetMapping
    @ApiOperation(value = "" , notes = "가구 List")
    public Page<FurnitureToFurnitureCategory> findByFurniturePerPage(@RequestParam("page") int page , @RequestParam("per_page") int page_page)
    {
        PageRequest pageRequest  = new PageRequest(page , page_page , Sort.Direction.ASC , "fid");

        return furnitureToFurnitureCategoryService.getByFurniturePerPage(pageRequest);
    }

    @PostMapping
    @ApiOperation(value = "" , notes = "가구 생성")
    public ResponseEntity insertToFurniture(@ModelAttribute Furniture furnitureParam)
    {
        Furniture furniture = furnitureService.insertToFurniture(furnitureParam);

        List<FurnitureCategory> furnitureCategoryList = new ArrayList<>();

        for(Integer index : furnitureParam.getTid())
        {
            furnitureCategoryList.add(new FurnitureCategory(furniture.getFid(),index));
        }

        furnitureCategoryService.insertToFurnitureCategory(furnitureCategoryList);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "" , notes = "가구 수정")
    public ResponseEntity updateToFurniture(@ModelAttribute Furniture furnitureParam)
    {
        Furniture furniture = furnitureService.updateToFurniture(furnitureParam);

        if(furniture == null) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "" , notes = "가구 삭제")
    public ResponseEntity deleteToFurniture(@RequestParam("fid") int fid)
    {
        Boolean resultByDelete = furnitureService.deleteToFurniture(fid);

        if(!resultByDelete)	throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{fid}")
    @ApiOperation(value = "" , notes = " {f_id}에 대한 가구 정보")
    public FurnitureToFurnitureCategory findByFurniture(@PathVariable("fid") int fid )
    {
        FurnitureToFurnitureCategory furnitureToFurnitureCategory = furnitureToFurnitureCategoryService.getByFurniture(fid);

        if(furnitureToFurnitureCategory == null)   throw new NotFoundException();

        return furnitureToFurnitureCategory ;
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "" , notes = " 모든 가구 List")
    public List<FurnitureToFurnitureCategory> findByFurnitureAll()
    {
        return furnitureToFurnitureCategoryService.getByFurnitureAll();
    }





}
