package com.lms.springboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.lms.springboot.jdbc.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class FileUtil
{
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
//		System.out.println("생성된 UUID : " + uuid);
		return uuid;
	}
	
	public static String renameFile(String dir, String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String uuid = getUuid();
		String newFileName = uuid + ext; // uuid + 확장자
		
		File oFile = new File(dir + File.separator + fileName);
		File sFile = new File(dir + File.separator + newFileName);
		oFile.renameTo(sFile);
		return newFileName;
	}
	
	public static UserDTO singleFileUpload(HttpServletRequest req, UserDTO dto, String partName) throws IOException, ServletException
	{
		String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
//		System.out.println("물리적경로 : " + uploadDir);
		
		Part part = req.getPart(partName);
		String partHeader = part.getHeader("content-disposition");
		String[] phArr = partHeader.split("filename=");
		String originalFileName = phArr[1].trim().replace("\"", "");
		if(!originalFileName.isEmpty()) {
			part.write(uploadDir + File.separator + originalFileName);
		}
		String savedFileName = FileUtil.renameFile(uploadDir, originalFileName);
		
//		System.out.println(originalFileName + " : " + savedFileName);
		dto.setOriginalFile(originalFileName);
		dto.setSaveFile(savedFileName);
		
		return dto;
	}
	
	public static ArrayList<UserDTO> boardFileUpload(HttpServletRequest req, UserDTO dto, String partName) throws IOException, ServletException {
		String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
//		System.out.println("물리적경로 : " + uploadDir);
		
//		Map<String, String> saveFileMaps = new HashMap<>();
		ArrayList<UserDTO> list = new ArrayList<>();
		
		Collection<Part> parts = req.getParts();
		for(Part part : parts) {
			if(!part.getName().equals(partName))
				continue;
			
			String partHeader = part.getHeader("content-disposition");
//			System.out.println("partHeader=" + partHeader);
			String[] phArr = partHeader.split("filename=");
			String originalFileName = phArr[1].trim().replace("\"", "");
			if(!originalFileName.isEmpty()) {
				part.write(uploadDir + File.separator + originalFileName);
			}
			String savedFileName = renameFile(uploadDir, originalFileName);
			list.add(UserDTO.builder()
					.board_idx(dto.getBoard_idx())
					.ofile(originalFileName)
					.sfile(savedFileName)
					.build());
		}
		
//		for(UserDTO file : list) {
//			System.out.println(file.getOfile() + "=" + file.getSfile() + " board_idx=" + file.getBoard_idx());
//			
//		}
		
		return list;
	}
	
	public static void downloadFile(String sfileName, String ofileName, HttpServletResponse resp) {
		String uploadDir;
		
		try
		{
			uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			String path = uploadDir + "\\" +sfileName;
			File file = new File(path);
			FileInputStream in = new FileInputStream(path);
			
			String fileNameCon = new String(ofileName.getBytes("UTF-8"), "8859_1");
			
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=" + fileNameCon);
			OutputStream os = resp.getOutputStream();
			
			int length;
			byte[] b = new byte[(int)file.length()];
			while((length = in.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			os.close();
			in.close();
		} catch (IOException e)
		{
			System.out.println("다운에러");
			e.printStackTrace();
		}
	}
	
	public static String getFiles(ArrayList<UserDTO> list) {
		String result = "";
		String uploadDir;
		try
		{
			uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
			for(UserDTO dto : list) {
//				System.out.println(uploadDir + dto.getSfile()); 
				if(list.getLast().equals(dto))
					result += "<span>" + dto.getOfile() +  "<a href='download.do?fileName=" + dto.getSfile() + "'>Download</a></span> &nbsp;&nbsp;";
				else
					result += "<span>" + dto.getOfile() +  "<a href='download.do?fileName=" + dto.getSfile() + "'>Download</a></span> &nbsp;&nbsp;";
			}
		} catch (FileNotFoundException e)
		{
			System.out.println("파일 받기 실패");
			e.printStackTrace();
		}
		
		return result;
	}
}
