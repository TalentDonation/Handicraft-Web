package com.handicraft.admin.controller;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.handicraft.core.dto.Image;
import com.handicraft.core.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
@Slf4j
public class ModelController {

	@Value("${bucketName}")
	private String bucketName;
	@Value("${accessKey}")
	private String accessKey;
	@Value("${secretKey}")
	private String secretKey;

	@Autowired
	ImageService imageService;

	@RequestMapping("/model")
	public ModelAndView getModel() throws UnsupportedEncodingException, IOException {

		ModelAndView mav = new ModelAndView();
		Image image = new Image();

		AWSCredentials crd = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).withCredentials(new AWSStaticCredentialsProvider(crd)).build();

		ObjectListing objects = s3.listObjects(bucketName,"");
		do {
			for(S3ObjectSummary objectSummary : objects.getObjectSummaries()){
				log.info(objectSummary.toString());
				System.out.println(s3.getUrl(bucketName,objectSummary.getKey()));
				image.setExtension(objectSummary.getKey().split(".")[1]);
				image.setName(s3.getUrl(bucketName,objectSummary.getKey()).toString());

				imageService.insertImages(image);
			}

		}while(objects.isTruncated());


		mav.setViewName("model");
		return mav;
	}

	private void printFile(File file, int depth) {
		if (depth > 10) {
			return;
		}

		if (file.isDirectory()) {
			for (int i = 0; i < depth; i++)
				System.out.print("  ");
			System.out.println(file.getName() + "/");
			File[] subFiles = file.listFiles();
			for (File subFile: subFiles) {
				printFile(subFile, depth + 1);
			}
		}
		else {
			for (int i = 0; i < depth; i++)
				System.out.print("  ");
			System.out.println(file.getName());
		}
	}

	// TODO: db에 등록날짜, 이름, 확장자 저장
	@PostMapping("/model/upload")
	public String uploadModelFile(@RequestParam("submitFile") MultipartFile file)
	{
		FileOutputStream fos;

		printFile(new File("../webapps/ROOT/static"), 0);
		System.out.println(System.getProperty("user.dir"));
		System.out.println("--------------절취선--------------");
//		System.out.println(ModelController.class.getResource("").getPath());
//		printFile(new File(ServletInitializer.class.getResource("").getPath()+"../../../../../../"),0);


		File newFile = new File("../webapps/ROOT/static/"+file.getOriginalFilename());

		try {
			boolean nf = newFile.createNewFile();
			System.out.println(nf);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}

		if(newFile.exists()) {
			try {
				byte fileData[] = file.getBytes();
				fos = new FileOutputStream("../webapps/ROOT/static/" + file.getOriginalFilename());
				fos.write(fileData);
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		System.out.println(file.getOriginalFilename());
		System.out.println(newFile.getName());

//		Image image = new Image();
//		image.setExtension(file.getOriginalFilename().split(".")[1]);
//
//		imageService.insertImages(image);





		return "redirect:/model";
	}
}
