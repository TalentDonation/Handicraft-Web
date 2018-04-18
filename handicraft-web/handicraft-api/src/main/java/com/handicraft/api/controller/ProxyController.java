package com.handicraft.api.controller;

import com.handicraft.api.exception.NotFoundException;
import com.handicraft.core.domain.Avatar;
import com.handicraft.core.domain.Image;
import com.handicraft.core.domain.User;
import com.handicraft.core.service.ImageService;
import com.handicraft.core.service.UserService;
import com.handicraft.core.support.AwsModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import static com.handicraft.core.support.FileModule.checkExtension;
import static com.handicraft.core.support.FileModule.readFile;

@Api(value = "user", description = "USER API")
@RestController
public class ProxyController {
    private final AwsModule awsService;
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public ProxyController(AwsModule awsService, ImageService imageService, UserService userService) {
        this.awsService = awsService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(value = "/images/{fileName}", produces = "image/**")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity imagesProxy(@PathVariable("fileName") String fileName) throws IOException {
        Image image = imageService.findOneByFileName(fileName);
        if (image == null || image.getFurniture() == null) {
            throw new IllegalArgumentException();
        }

        String path = image.getName() + "." + image.getExtension();

        byte[] media = awsService.load(image.getFurniture().getFid(), path);
        Optional<MediaType> mediaType = checkExtension(image.getExtension());
        if (!mediaType.isPresent()) {
            throw new NotFoundException();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType.get())
                .body(media);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(value = "/avatar/{fileName}", produces = "image/**")
    @ApiImplicitParam(name = "authorization", value = "authorization", dataType = "string", paramType = "header")
    public ResponseEntity<byte[]> avatarProxy(@PathVariable("fileName") String fileName) throws IOException {
        User user = userService.findOneByFileName(fileName);
        if (user == null || user.getAvatar() == null) {
            throw new IllegalArgumentException();
        }

        Avatar avatar = user.getAvatar();
        byte[] media = readFile(fileName);
        Optional<MediaType> mediaType = checkExtension(avatar.getExtension());
        if (!mediaType.isPresent()) {
            throw new NotFoundException();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType.get())
                .body(media);
    }

}
