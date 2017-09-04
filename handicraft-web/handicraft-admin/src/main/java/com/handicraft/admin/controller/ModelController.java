package com.handicraft.admin.controller;


import com.handicraft.core.dto.Image;
import com.handicraft.core.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ModelController {

	@Autowired
	ImageService imageService;

	@RequestMapping("/model")
	public ModelAndView getModel() throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

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

		Image image = new Image();
		image.setExtension(file.getOriginalFilename().split(".")[1]);

		imageService.insertImages(image);



		return "redirect:/model";
	}
}
