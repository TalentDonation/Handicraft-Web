package com.handicraft.api.controller;

import com.handicraft.core.dto.FurnitureDto;
import com.handicraft.core.service.CommentService;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.ImageService;
import com.handicraft.core.support.AwsModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@Api(value = "furniture", description = "Furniture API")
@RestController
public class FurnitureController {
    private final FurnitureService furnitureService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final AwsModule awsModule;

    @Autowired
    public FurnitureController(FurnitureService furnitureService, ImageService imageService, AwsModule awsModule, CommentService commentService) {
        this.furnitureService = furnitureService;
        this.imageService = imageService;
        this.awsModule = awsModule;
        this.commentService = commentService;
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(value = "/furniture", produces = "application/json")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createFurniture(@RequestBody FurnitureDto furnitureDto) {
        furnitureService.create(furnitureDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(value = "/furniture", produces = "application/json")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findFurniture(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return ResponseEntity.ok(furnitureService.find(page, perPage));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurniture(@RequestBody List<FurnitureDto> furnitureDtos) {
        furnitureService.update(furnitureDtos);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findFurnitureOne(@PathVariable("fid") long fid) {
        return ResponseEntity.ok(furnitureService.findOne(fid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurnitureOne(@RequestBody FurnitureDto furnitureDto) {
        furnitureService.updateOne(furnitureDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/furniture/{fid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeFurnitureOne(@PathVariable("fid") long fid) {
        furnitureService.remove(fid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/furniture/{fid}/images")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity createFurnitureImages(@PathVariable("fid") long fid, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        imageService.upload(fid, multipartFile);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/furniture/{fid}/images")
    @ApiOperation(value = "", notes = "Get images by furniture id")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findFurnitureImages(@PathVariable("fid") long fid) {
        return ResponseEntity.ok(imageService.findByFid(fid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/furniture/{fid}/images")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeFurnitureImages(@PathVariable("fid") long fid) throws FileNotFoundException {
        imageService.removeAllByFid(fid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/furniture/{fid}/images/{gid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findFurnitureImage(@PathVariable("fid") long fid, @PathVariable("gid") long gid) {
        return ResponseEntity.ok(imageService.findOne(gid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/furniture/{fid}/images/{gid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurnitureImage(@PathVariable("fid") long fid, @PathVariable("gid") long gid, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        imageService.change(fid, gid, multipartFile);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/furniture/{fid}/images/{gid}")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeFurnitureImage(@PathVariable("fid") long fid, @PathVariable("gid") long gid) throws FileNotFoundException {
        imageService.remove(gid);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/furniture/{fid}/comments")
    @ApiOperation(value = "", notes = "find comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity findFurnitureComments(@PathVariable("fid") long fid) {
        return ResponseEntity.ok(commentService.findByFid(fid));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/furniture/{fid}/comments")
    @ApiOperation(value = "", notes = "delete comment about furniture")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity removeFurnitureComments(@PathVariable("fid") long fid) {
        commentService.removeByFid(fid);
        return ResponseEntity.ok().build();
    }
}
