package com.lms.springboot.utils;

import java.io.File;
import java.util.UUID;

<<<<<<< HEAD
public class MyFunctions
{
	/*
		UUID(Universally Unique IDentifier) : 직역하면 범용 고유 식별자라고한다.
			JDK에서 기본적으로 제공되는 클래스로 32자의 영문과 숫자를 포함한 고유한
			문자열을 반환해준다. 
=======

public class MyFunctions
{
	/*
		UUID(Universally Unique IDentifier) : a 128 bit number
>>>>>>> origin/master
	 */
	public static String getUuid()
	{
		String  uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		System.out.println("생성된 UUID :" + uuid);
		return uuid;
	}
	
<<<<<<< HEAD
	//파일명 변경 
	public static String renameFile(String sDirectory, String fileName)
	{
		//파일의 확장자를 파일명의 끝에서부터 잘라낸다. 
		String ext = fileName.substring(fileName.lastIndexOf("."));
		//파일명으로 사용할 UUID를 얻어온다. 
		String now = getUuid();
		//둘을 합쳐 새로운 파일명을 만든다.
		String newFileName = now + ext;
		
		//기존파일과 새로운파일의 객체를 만든 후 이름을 변경한다. 
=======
	// filerename 
	public static String renameFile(String sDirectory, String fileName)
	{
		// Split the filename extension 
		String ext = fileName.substring(fileName.lastIndexOf("."));
		// get the new file name(UUID)
		String now = getUuid();
		// Save as new file name + extension
		String newFileName = now + ext;
		
>>>>>>> origin/master
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		
<<<<<<< HEAD
		//변경된 파일명을 반환한다. 
		return newFileName;
	}
=======
		return newFileName;
	}	
>>>>>>> origin/master
}
