package com.handicraft.api.controller;

import com.handicraft.api.exception.InternalServerErrorException;
import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.dto.Furniture;
import com.handicraft.core.dto.FurnitureAbs;
import com.handicraft.core.dto.FurnitureToImage;
import com.handicraft.core.dto.Image;
import com.handicraft.core.service.FurnitureService;
import com.handicraft.core.service.FurnitureToImageService;
import com.handicraft.core.service.ImageService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @PostMapping("/furniture")
    @ApiOperation(value = "" , notes = "Create a new furniture")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity insertFurnitureByFid(@ModelAttribute("furniture") Furniture furniture , MultipartFile multipartFile , @AuthenticationPrincipal Long uid) throws IOException {

        furniture.setFid(0);
        FurnitureToImage furnitureToImage = new FurnitureToImage(furniture);

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

        FurnitureToImage result = furnitureToImageService.insertFurnitureToImageByFid(furnitureToImage);


        StringBuffer uri = new StringBuffer();
                uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                .append("/").append(result.getImageList().get(0).getGid())
                .append(".").append(result.getImageList().get(0).getExtension());

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
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public Furniture findByFurnitureByFid(@PathVariable("fid") long fid )
    {


       FurnitureToImage furnitureToImage = furnitureToImageService.findFurnitureToImageByFid(fid);

       if(furnitureToImage == null)   throw new NotFoundException();

       Furniture furniture = new Furniture (furnitureToImage);

       List<String> imageList = new ArrayList<>();

       for(Image image : furnitureToImage.getImageList())
       {
            imageList.add(image.getName()+image.getExtension());
       }

       furniture.setImages(imageList);

        return  furniture;
    }

    @PutMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Update one furniture about furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity updateFurnitureById(@ModelAttribute Furniture furniture)
    {
        Furniture furnitureResult = furnitureService.updateFurnitureByFid(furniture);

        if(furnitureResult == null) throw new NotFoundException();

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}")
    @ApiOperation(value = "" , notes = "Delete one furniture about furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
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

    @PostMapping("/furniture/{fid}/images")
    @ApiOperation(value = "" , notes = "Insert images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity InsertImagesByFid(@PathVariable("fid") long fid , @RequestParam("images") MultipartFile multipartFile)
    {

        FurnitureToImage furnitureToImage = furnitureToImageService.findFurnitureToImageByFid(fid);

        List<Image> images = furnitureToImage.getImageList();

        File file;
        // upload file

        String[] originFile = multipartFile.getOriginalFilename().split("\\.");
        Date currentDateTime = new Date();
//        String dateTime = dateFormat.format(currentDateTime);

        Image image = new Image();
        image.setGid(0);
        image.setCreateAt(currentDateTime);
        image.setUpdateAt(currentDateTime);
        image.setExtension(originFile[1]);
        image.setName(""+image.getGid());

        images.add(image);
        furnitureToImage.setImageList(images);

        StringBuffer uri = new StringBuffer();
        try {
            uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                    .append("/").append(image.getName())
                    .append(".").append(originFile[1]);

            file = new File(uri.toString());

            multipartFile.transferTo(file);

        } catch (IOException e) {
            e.printStackTrace();
            new InternalServerErrorException();
        }

        furnitureToImageService.updateFurnitureToImageByFid(furnitureToImage);

        return new ResponseEntity(HttpStatus.OK);
    }


    /*
    *
    *   image 1개
    * */

    @GetMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Get images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public String findImagesByFidAndGid(@PathVariable("fid") long fid , @PathVariable("gid") long gid)
    {
        Image image = imageService.findImageByGid(gid);

        return image.getName()+"."+image.getExtension();
    }


    @PutMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Update images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity UpdateImagesByFidAndGid(@RequestParam("fid") long fid , @RequestParam("gid") long gid , MultipartFile multipartFile)
    {

        String[] originFile = multipartFile.getOriginalFilename().split("\\.");

        Image image = imageService.findImageByGid(gid);
        image.setExtension(originFile[1]);

        StringBuffer stringBuffer = new StringBuffer();
        File file;
        try {
            stringBuffer.append(ResourceUtils.getFile("classpath:static/images").getPath())
                        .append("/").append(gid)
                        .append(".").append(originFile[1]);
            file = new File(stringBuffer.toString());
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        imageService.updateImagesByGid(image);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/furniture/{fid}/images/{gid}")
    @ApiOperation(value = "" , notes = "Delete images by furniture id")
    @ApiImplicitParam(name = "authorization", value="authorization", dataType = "string", paramType = "header")
    public ResponseEntity DeleteImagesByFidAndGid(@RequestParam("fid") long fid , @RequestParam("gid") long gid)
    {

        Image image = imageService.findImageByGid(gid);

        StringBuffer stringBuffer = new StringBuffer();
        File file;
        try {
            stringBuffer.append(ResourceUtils.getFile("classpath:static/images").getPath())
                    .append("/").append(image.getGid())
                    .append(".").append(image.getExtension());
            file = new File(stringBuffer.toString());
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }




}
