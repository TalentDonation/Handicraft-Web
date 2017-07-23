package com.handicraft.admin.controller;


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
	Environment env;
//	ApplicationContext context;

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

	@PostMapping("/model/upload")
	public String uploadModelFile(@RequestParam("submitFile") MultipartFile file)
	{
		FileOutputStream fos;
//		Calendar calendar = Calendar.getInstance();
//		Date date = calendar.getTime();
//		String fileName = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(date) + ".html";


		printFile(new File("../webapps/ROOT/static"), 0);
		System.out.println(System.getProperty("user.dir"));
		System.out.println("--------------절취선--------------");
//		System.out.println(ModelController.class.getResource("").getPath());
//		printFile(new File(ServletInitializer.class.getResource("").getPath()+"../../../../../../"),0);



//		try {
//			byte fileData[] = file.getBytes();
//			fos = new FileOutputStream("../webapps/ROOT/static/");
//			fos.write(fileData);
//			fos.close();
//		}
//		catch(IOException ex){
//			ex.printStackTrace();
//		}

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
				fos = new FileOutputStream("../webapps/ROOT/static/"+file.getOriginalFilename());
				fos.write(fileData);
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		System.out.println(file.getOriginalFilename());
		System.out.println(newFile.getName());

//		File convFile = new File();

//		try {
//			Path convFile = Files.createFile(Paths.get("../webapps/ROOT/static"));
//			Path target = Paths.get("../webapps/ROOT/static");
//			Files.move(convFile,target.resolve(convFile.getFileName()));
//		}
//		catch(IOException ex){
//			ex.printStackTrace();
//			System.out.println("error occurred!");
//		}




		return "redirect:/model";
	}
}
