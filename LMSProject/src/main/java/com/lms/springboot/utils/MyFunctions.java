package com.lms.springboot.utils;

import java.io.File;
import java.util.UUID;


public class MyFunctions
{
	/*
		UUID(Universally Unique IDentifier) : a 128 bit number
	 */
	public static String getUuid()
	{
		String  uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		System.out.println("생성된 UUID :" + uuid);
		return uuid;
	}
	
	// filerename 
	public static String renameFile(String sDirectory, String fileName)
	{
		// Split the filename extension 
		String ext = fileName.substring(fileName.lastIndexOf("."));
		// get the new file name(UUID)
		String now = getUuid();
		// Save as new file name + extension
		String newFileName = now + ext;
		
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		
		return newFileName;
	}	
}
