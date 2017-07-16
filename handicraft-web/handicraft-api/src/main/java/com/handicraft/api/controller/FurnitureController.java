package com.handicraft.api.controller;

import com.handicraft.api.util.ResponseStatus;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.User;
import com.handicraft.core.service.FurnitureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{f_id}")
    @ApiOperation(value = "" , notes = " {f_id}에 대한 가구 정보")
    public ResponseEntity<?> getByFurniture(@PathVariable("f_id") int f_id )
    {
        Furniture furniture = furnitureService.getByFurniture(f_id);

        if(furniture == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Furniture>(furniture , HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "" , notes = " 모든 가구 List")
    public ResponseEntity<?> getByFurnitureAll()
    {
        return new ResponseEntity<List<Furniture>>(furnitureService.getByFurnitureAll() , HttpStatus.OK);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "" , notes = "가구 생성")
    public ResponseEntity<?> insertToFurniture(@ModelAttribute Furniture furnitureParam)
    {
        Furniture furniture = furnitureService.insertToFurniture(furnitureParam);

        if(furniture == null)   return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Furniture>(furniture, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @ApiOperation(value = "" , notes = "가구 수정")
    public ResponseEntity<?> updateToFurniture(@ModelAttribute Furniture furnitureParam)
    {
        Furniture furniture = furnitureService.updateToFurniture(furnitureParam);

        if(furniture == null) return  new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Furniture>(furniture , HttpStatus.OK);
    }

}
