package com.handicraft.api.controller;

import com.handicraft.api.exception.InternalServerErrorException;
import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToImage;
import com.handicraft.core.dto.Image;
import com.handicraft.core.service.FurnitureCategoryService;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.FurnitureToImageService;
import com.handicraft.core.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@Api(value = "furniture" , description = "Furniture API")
public class FurnitureController {

    /*
   * DI
   *
   * */
    @Autowired
    FurnitureService furnitureService;

    @Autowired
    FurnitureToImageService furnitureToImageService;

    @Autowired
    FurnitureCategoryService furnitureCategoryService;

    @Autowired
    ImageService imageService;


    @GetMapping("/furniture")
    @ApiOperation(value = "" , notes = "Show several furniture by page")
    public List<Furniture> findByFurniturePerPage(@RequestParam(value = "page" , defaultValue = "0")int page , @RequestParam(value = "per_page",defaultValue = "10") int page_page)
    {
        PageRequest pageRequest  = new PageRequest(page , page_page , Sort.Direction.ASC , "fid");

        Page<FurnitureToImage> furnitureToImagePage = furnitureToImageService.findFurniturToImagePerPage(pageRequest);

        List<Furniture> furnitureList  = new ArrayList<>();

        for(FurnitureToImage furnitureToImage : furnitureToImagePage)
        {
            Furniture furniture = new Furniture(furnitureToImage);
            List<String> imageLists = null;
            if(!furnitureToImage.getImageList().isEmpty())
            {
                imageLists = new ArrayList<>();

                for(Image image : furnitureToImage.getImageList())
                {
                    imageLists.add(image.getUri()+image.getExtension());
                }
            }
            furniture.setImages(imageLists);
            furnitureList.add(furniture);
        }


        return furnitureList;
    }

    @PostMapping("/furniture")
    @ApiOperation(value = "" , notes = "Create a new furniture")
    public ResponseEntity insertFurnitureByFid(@ModelAttribute Furniture furniture , MultipartFile multipartFile) throws IOException {
        FurnitureToImage furnitureToImage = new FurnitureToImage(furniture);

        File file;
        Image image;

        // upload file
        Resource resource = new ClassPathResource("static");

        String[] originFile = multipartFile.getOriginalFilename().split("\\.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateTime = new Date();
        String dateTime = dateFormat.format(currentDateTime);

        image = new Image();
        image.setGid(imageService.findImageByLastIndex().getGid() + 1);
        image.setExtension(originFile[1]);
        image.setRegisterAt(dateTime);
        image.setUri(""+image.getGid());

        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        furnitureToImage.setImageList(imageList);

        StringBuffer uri = new StringBuffer();
                uri.append(resource.getFile())
                .append("/").append(image.getUri())
                .append(".").append(originFile[1]);

        file = new File(uri.toString());

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            new InternalServerErrorException();
        }

        furnitureToImageService.insertFurnitureToImageByFid(furnitureToImage);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/furniture")
    @ApiOperation(value = "" , notes = "Update multiple furniture")
    public ResponseEntity updateFurnitureList(@ModelAttribute List<Furniture> furnitureList)
    {
        List<Furniture> furnitureListResult = furnitureService.updateFurnitureList(furnitureList);

        if(furnitureListResult == null) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping("/furniture")
    @ApiOperation(value = "" , notes = "Delete all furniture")
    public ResponseEntity deleteFurnitureList()
    {
        furnitureService.deleteFurnitureList();

        return new ResponseEntity(HttpStatus.OK);
    }

     /*
    * Specific Layer
    *
    * */

    @GetMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Show a furniture about furniture id")
    public Furniture findByFurnitureByFid(@PathVariable("fid") long fid )
    {
        Furniture furniture = furnitureService.findFurnitureByFid(fid);

        if(furniture == null)   throw new NotFoundException();

        return furniture ;
    }

    @PutMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Update one furniture about furniture id")
    public ResponseEntity updateFurnitureById(@ModelAttribute Furniture furniture)
    {
        Furniture furnitureResult = furnitureService.updateFurnitureByFid(furniture);

        if(furnitureResult == null) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Delete one furniture about furniture id")
    public ResponseEntity deleteFurnitureById(@PathVariable("fid") long fid )
    {
        Boolean resultOfDelete = furnitureToImageService.deleteFurnitureToImageByFid(fid);

        if(!resultOfDelete)	throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    *
    * f_id 에 대한 전체 images
    * */

    @GetMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Get images by furniture id")
    public Furniture findImagesByFid(@PathVariable("fid") long fid)
    {
        return furnitureService.findFurnitureByFid(fid);
    }

    @PostMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Insert images by furniture id")
    public ResponseEntity InsertImagesByFid(@PathVariable("fid") long fid , @RequestParam("src/main/static/images") MultipartFile multipartFile)
    {
        Furniture furniture = furnitureService.findFurnitureByFid(fid);

//        List<Image> imageList = furniture.getImages();
//        imageList.add(new Image(furniture.getFid(),"test",".jfd", furniture.getCreateAt()));

        furnitureService.updateFurnitureByFid(furniture);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Update images by furniture id")
    public ResponseEntity UpdateImagesByFid(@PathVariable("fid") long fid , @ModelAttribute List<Image> imageList)
    {
        Furniture furniture = furnitureService.findFurnitureByFid(fid);

//        furniture.setImages(imageList);

        furnitureService.updateFurnitureByFid(furniture);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Delete images by furniture id")
    public ResponseEntity DeleteImagesByFid(@PathVariable("fid") long fid)
    {
        furnitureService.deleteImagesByFid(fid);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    *
    *   image 1개
    * */

    @GetMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Get images by furniture id")
    public List<Image> findImagesByFidAndGid(@PathVariable("fid") long fid , @PathVariable("gid") long gid)
    {
        return null;
    }


    @PutMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Update images by furniture id")
    public ResponseEntity UpdateImagesByFidAndGid(@RequestParam("fid") long fid)
    {


        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Delete images by furniture id")
    public ResponseEntity DeleteImagesByFidAndGid()
    {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }




}
