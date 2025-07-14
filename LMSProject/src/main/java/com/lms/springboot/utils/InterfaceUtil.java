package com.lms.springboot.utils;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

public class InterfaceUtil
{
	public static void alertLocation(HttpServletResponse resp, String msg, String location){
		try
		{
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.write("<script>"
					+ " alert('" + msg + "');"
					+ " location.href='" + location + "'"
					+ "</script>");
			out.flush();
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void alert(HttpServletResponse resp, String msg){
		try
		{
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.write("<script>alert('" + msg + "');</script>");
			out.flush();
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
