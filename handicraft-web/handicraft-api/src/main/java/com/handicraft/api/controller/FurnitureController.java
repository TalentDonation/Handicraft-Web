package com.handicraft.api.controller;

import com.handicraft.api.exception.InternalServerErrorException;
import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureToImage;
import com.handicraft.core.dto.Image;
import com.handicraft.core.dto.UserToFurniture;
import com.handicraft.core.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 고승빈 on 2017-07-06.
 */

@RestController
@Slf4j
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
    UserToFurnitureService userToFurnitureService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;


    @Value("${images-path}")
    String imagesPath;


    @GetMapping("/furniture")
    @ApiOperation(value = "" , notes = "Show several furniture by page")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
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
                    imageLists.add(image.getName()+image.getGid()+"."+image.getExtension());
                }
            }
            furniture.setImages(imageLists);
            furnitureList.add(furniture);
        }


        return furnitureList;
    }

    @PostMapping(value = "/furniture")
    @ApiOperation(value = "" , notes = "Create a new furniture")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity insertFurnitureByFid(@ModelAttribute("furniture") Furniture furniture , MultipartFile multipartFile , @AuthenticationPrincipal Long uid) throws IOException {

        furniture.setFid(0);
        FurnitureToImage furnitureToImage = new FurnitureToImage(furniture);

        UserToFurniture userToFurniture = userToFurnitureService.find(uid);
        List<FurnitureToImage> furnitureToImageList = userToFurniture.getFurnitureToImageList();


        File file;
        Image image;

        // upload file
        image = new Image();
        image.setGid(0);
        image.setExtension(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        image.setCreateAt(null);
        image.setUpdateAt(null);
        image.setName(imagesPath);

        log.info(imagesPath);


        List<Image> imageList = new ArrayList<>();
        imageList.add(image);
        furnitureToImage.setImageList(imageList);

        furnitureToImageList.add(furnitureToImage);
        userToFurniture.setFurnitureToImageList(furnitureToImageList);

        UserToFurniture result = userToFurnitureService.update(userToFurniture);


        StringBuffer uri = new StringBuffer();
                uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(result.getFurnitureToImageList().get(result.getFurnitureToImageList().size() - 1).getImageList().get(0).getGid())
                .append(".").append(result.getFurnitureToImageList().get(result.getFurnitureToImageList().size() - 1).getImageList().get(0).getExtension());

        file = new File(uri.toString());


        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            new InternalServerErrorException();
        }


        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/furniture")
    @ApiOperation(value = "" , notes = "Update multiple furniture")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurnitureList(@ModelAttribute List<Furniture> furnitureList)
    {
        List<Furniture> furnitureListResult = furnitureService.updateFurnitureList(furnitureList);

        if(furnitureListResult == null) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping("/furniture")
    @ApiOperation(value = "" , notes = "Delete all furniture")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity deleteFurnitureList() throws FileNotFoundException {
        List<FurnitureToImage> furnitureToImageList = furnitureToImageService.find();

        File file;
        StringBuffer uri;

        for(FurnitureToImage furnitureToImage : furnitureToImageList)
        {
            for(Image image : furnitureToImage.getImageList())
            {
                uri = new StringBuffer();
                uri.append(ResourceUtils.getFile("classpath:static/images"))
                        .append("/").append(image.getGid())
                        .append(".").append(image.getExtension());
                file = new File(uri.toString());
                file.delete();
            }

        }
        furnitureToImageService.delete();

        return new ResponseEntity(HttpStatus.OK);
    }

     /*
    * Specific Layer
    *
    * */

    @GetMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Show a furniture about furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public Furniture findByFurnitureByFid(@PathVariable("fid") long fid )
    {

       FurnitureToImage furnitureToImage = furnitureToImageService.findById(fid);

       if(furnitureToImage == null)   throw new NotFoundException();

       Furniture furniture = new Furniture (furnitureToImage);

       List<String> imageList = new ArrayList<>();

       for(Image image : furnitureToImage.getImageList())
       {
            imageList.add(image.getName()+image.getGid()+"."+image.getExtension());
       }

       furniture.setImages(imageList);

        return  furniture;
    }

    @PutMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Update one furniture about furniture id" )
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurnitureById(@ModelAttribute Furniture furniture , @AuthenticationPrincipal Long uid)
    {
        UserToFurniture userToFurniture = userToFurnitureService.find(uid);

        for(FurnitureToImage furnitureToImage : userToFurniture.getFurnitureToImageList())
        {
            if(furnitureToImage.getFid() == furniture.getFid())
            {
                furniture.setCreateAt(furnitureToImage.getCreateAt());
                FurnitureToImage newFurnitureToImage = new FurnitureToImage(furniture);
                newFurnitureToImage.setImageList(furnitureToImage.getImageList());

                userToFurniture.getFurnitureToImageList().set(userToFurniture.getFurnitureToImageList().indexOf(furnitureToImage) , newFurnitureToImage);
                break;
            }
        }

        userToFurnitureService.update(userToFurniture);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Delete one furniture about furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity deleteFurnitureById(@PathVariable("fid") long fid ) throws FileNotFoundException {
        FurnitureToImage furnitureToImage = furnitureToImageService.findById(fid);

        File file;
        StringBuffer uri;

        for(Image image : furnitureToImage.getImageList())
        {
            uri = new StringBuffer();
            uri.append(ResourceUtils.getFile("classpath:static/images"))
                    .append("/").append(image.getGid())
                    .append(".").append(image.getExtension());
            file = new File(uri.toString());
            file.delete();
        }


        furnitureToImageService.deleteById(furnitureToImage.getFid());

        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    *
    * f_id 에 대한 전체 images
    * */

    @PostMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Insert images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity InsertImagesByFid(@PathVariable("fid") long fid , @RequestParam("images") MultipartFile multipartFile) throws IOException {

        FurnitureToImage furnitureToImage = furnitureToImageService.findById(fid);

        List<Image> images = furnitureToImage.getImageList();

        File file;

        Image image = new Image();
        image.setGid(0);
        image.setExtension(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        image.setCreateAt(null);
        image.setUpdateAt(null);
        image.setName(imagesPath);

        images.add(image);
        furnitureToImage.setImageList(images);

        StringBuffer uri = new StringBuffer();

        FurnitureToImage result = furnitureToImageService.updateFurnitureToImageByFid(furnitureToImage);

        uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(result.getImageList().get(result.getImageList().size() - 1).getGid())
                .append(".").append(result.getImageList().get(result.getImageList().size() - 1).getExtension());

        file = new File(uri.toString());

        multipartFile.transferTo(file);

        return new ResponseEntity(HttpStatus.OK);
    }


    /*
    *
    *   image 1개
    * */

    @GetMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Get images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public List<String> findImagesByFid(@PathVariable("fid") long fid )
    {

        FurnitureToImage furnitureToImage = furnitureToImageService.findById(fid);

        if(furnitureToImage == null)   throw new NotFoundException();


        List<String> imageList = new ArrayList<>();

        for(Image image : furnitureToImage.getImageList())
        {
            imageList.add(image.getName()+image.getGid()+"."+image.getExtension());
        }

        return  imageList;
    }

    @DeleteMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Delete images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity DeleteImagesByFid(@RequestParam("fid") long fid ) throws FileNotFoundException {
        FurnitureToImage furnitureToImage = furnitureToImageService.findById(fid);

        List<Image> imageList = furnitureToImage.getImageList();
        StringBuffer stringBuffer;
        File file;

        for(Image image : imageList)
        {
            stringBuffer = new StringBuffer()
                    .append(ResourceUtils.getFile("classpath:static/images").getPath())
                    .append("/").append(image.getGid())
                    .append(".").append(image.getExtension());

            file = new File(stringBuffer.toString());
            file.delete();
        }

        imageService.delete(imageList);


        return new ResponseEntity(HttpStatus.OK);
    }


    @PutMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Update images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity UpdateImagesByFidAndGid(@RequestParam("fid") long fid , @RequestParam("gid") long gid , MultipartFile multipartFile) throws IOException {
        Image image = imageService.findById(gid);

        StringBuffer stringBuffer;
        File file;

        stringBuffer = new StringBuffer()
                .append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(image.getGid())
                .append(".").append(image.getExtension());

        file = new File(stringBuffer.toString());

        file.delete();

        image.setExtension(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
//        image.setUpdateAt(null);

        stringBuffer = new StringBuffer()
                .append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(image.getGid())
                .append(".").append(image.getExtension());

        file = new File(stringBuffer.toString());

        multipartFile.transferTo(file);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Delete images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity DeleteImagesByFidAndGid(@RequestParam("fid") long fid , @RequestParam("gid") long gid) throws FileNotFoundException {

        Image image = imageService.findById(gid);

        StringBuffer stringBuffer;
        File file;

        stringBuffer = new StringBuffer()
                .append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(image.getGid())
                .append(".").append(image.getExtension());

        file = new File(stringBuffer.toString());

        file.delete();

        imageService.deleteById(image.getGid());

        return new ResponseEntity(HttpStatus.OK);
    }





}
