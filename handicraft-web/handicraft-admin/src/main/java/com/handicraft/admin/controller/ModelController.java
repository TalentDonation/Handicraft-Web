package com.handicraft.admin.controller;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.handicraft.core.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ModelController {

    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;

    private List<AWSInfo> folderList = new ArrayList<>();
    private List<AWSInfo> fileList = new ArrayList<>();
    private String path = "";

    private AmazonS3 authorize() {
        AWSCredentials crd = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).withCredentials(new AWSStaticCredentialsProvider(crd)).build();
        return s3client;
    }

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public ModelAndView getModel() throws IOException {

        ModelAndView mav = new ModelAndView();
        AmazonS3 s3client = authorize();

        String navPath = "";
        String paths[] = path.split("/");
        List<AWSInfo> output = new ArrayList<>();

        // 폴더들의 path 만들어 준다
        for (String name : paths) {
            navPath += name + "/";
            output.add(new AWSInfo(name, navPath));
        }

        showAWSData(s3client);

        mav.addObject("awsFolder", folderList);
        mav.addObject("awsFile", fileList);
        mav.addObject("awsPath",output);

        log.info("---------------------------------------------------------");

        mav.setViewName("model");
        return mav;
    }

    // TODO: db에 등록날짜, 이름, 확장자 저장
    @PostMapping("/model/upload")
    public String uploadModelFile(@RequestParam("submitFile") MultipartFile file) throws IOException {
        String subDir;
        String uploadDir;

        if(!path.equals("")) {
            subDir = path.substring(0, path.lastIndexOf("/"));
        }
        else {
            subDir = path;
        }

        if(path.equals("")) {
            uploadDir = bucketName;
        }

        else {
            uploadDir = bucketName.concat("/").concat(subDir);
        }

        AmazonS3 s3Client = authorize();
        s3Client.putObject(new PutObjectRequest(uploadDir, file.getOriginalFilename(), file.getInputStream(), new ObjectMetadata()));
        // 서브디렉토리에 넣어줄때 디렉토리를 더해준다, 뒤에 / 붙이면 하위에 폴더 하나 만든다음에 올린다. 앞에 / 붙여야한다

        return "redirect:/model";
    }

    @PostMapping("/model/newfolder")
    public String createNewDirectory(@RequestParam("folderName") String folderName) throws IOException {

        AmazonS3 s3client = authorize();
        s3client.putObject(new PutObjectRequest(bucketName, path + folderName + "/","/model"));

        return "redirect:/model";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteObject(@RequestParam(value = "path") String dir)
    {
        AmazonS3 s3client = authorize();
        log.info("This is dir!!  " + dir);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, dir));

        List<String> response = new ArrayList<>();
        response.add(dir);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // 폴더 클릭했을때 폴더 안으로 들어가는 동작 수행
    @RequestMapping(value = "/forward", method = RequestMethod.GET)
    public ResponseEntity gotoForward(@RequestParam(value = "path") String dir)
    {
        path = dir;
        List<List<AWSInfo>> response = new ArrayList<>();
        response.add(folderList);
        response.add(fileList);

        log.info("successful!");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // 뒤로 갈수있는 동작 수행
    @RequestMapping(value = "/backward", method = RequestMethod.GET)
    public ResponseEntity gotoBackward(@RequestParam(value = "path") String dir)
    {
        List<String> response = new ArrayList<>();
        path = dir;
        response.add(path);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private void showAWSData(AmazonS3 s3client) {
        folderList.clear();
        fileList.clear();

        ListObjectsRequest listObject = new ListObjectsRequest();
        listObject.setBucketName(bucketName);
        log.info("It's in!!!!!!!!" + path);
        listObject.setPrefix(path);             // 여길 바꾸면 내용도 바뀐다 -> ajax 쓸때 data로 넘겨준다

        ObjectListing objects = s3client.listObjects(listObject);

        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                String name = objectSummary.getKey();
                log.info(name);

                AWSInfo awsInfo = new AWSInfo();

                if (path.equals("") && name.split("/").length == 1)    // path = "" 이고 최상위 일때
                {
                    addToLists(name, awsInfo, objectSummary, s3client);
                }
                else if (name.contains(path))
                {
                    String subDirectory = name.substring(path.lastIndexOf("/") + 1);
                    String slash[] = subDirectory.split("/");
                    int cntSlash = slash.length;

                    if (cntSlash == 1) {
                        addToLists(subDirectory, awsInfo, objectSummary, s3client);
                    }
                }
            }

            listObject.setMarker(objects.getNextMarker());
        } while (objects.isTruncated());

    }

    private void addToLists(String dir, AWSInfo awsInfo, S3ObjectSummary objectSummary, AmazonS3 s3client) {
        if (!dir.equals(""))
        {
            if (dir.contains("."))
            {
                awsInfo.setName(dir);
                setAWSInfo(awsInfo, objectSummary, s3client);
                fileList.add(awsInfo);
            }

            else {
                awsInfo.setName(dir.split("/")[0]);
                setAWSInfo(awsInfo, objectSummary, s3client);
                folderList.add(awsInfo);
            }
        }
    }

    private void setAWSInfo(AWSInfo awsInfo, S3ObjectSummary objectSummary, AmazonS3 s3client)
    {
        awsInfo.setLastModified(objectSummary.getLastModified().toString());
        awsInfo.setUrl(s3client.getUrl(bucketName, objectSummary.getKey()).toString());
        awsInfo.setPath(objectSummary.getKey());
    }

    public class AWSInfo {
        private String name;
        private String lastModified;
        private String url;
        private String path;

        public AWSInfo() { }

        public AWSInfo(String n, String p) {
            name = n;
            path = p;
        }

        public String getName() {
            return name;
        }

        public String getLastModified() {
            return lastModified;
        }

        public String getUrl() {
            return url;
        }

        public String getPath() {
            return path;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
