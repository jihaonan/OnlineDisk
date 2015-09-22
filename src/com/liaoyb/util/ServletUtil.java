package com.liaoyb.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;


public class ServletUtil {
	
	//检查用户是否登陆,如果登陆返回user,否则返回null,
	public static User checkUserLogin(HttpServletRequest request, HttpServletResponse response){
		Object objUser=request.getSession().getAttribute("user");
		User user=null;
		if(objUser!=null){
			user=(User) objUser;
		}
		
		return user;
		
	}
	
	//检查用户登录，如果没有登陆，先浏览器发送json提示信息
	public static User checkUserLogMess(HttpServletRequest request, HttpServletResponse response){
		User user=checkUserLogin(request, response);
		if(user==null){
			/* 设置格式为text/json */
			response.setContentType("text/json");
			/* 设置字符集为'UTF-8' */
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//当前未登录，跳转到登陆页面
			System.out.println("当前用户未登录");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			out.print(Json.writeMess("当前用户未登录"));
			
		}
		return user;
	}
	public static List<MyFile> getAllFilesInSession(HttpServletRequest request){
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		}
		return allFiles;
	}
	
	
	public static void removeMyFileInSession(HttpServletRequest request,String fileiddisk){
		//如果session中存在
		List<MyFile> allFiles=getAllFilesInSession(request);
		if(allFiles==null)
			return;
		
		//遍历
		for(MyFile file:allFiles){
			if(file.getFileIdInDisk().equals(fileiddisk)){
				MyFile del=file;
				allFiles.remove(del);
				return;
			}
			
		}
	}
	
	
	public static  void clearLoginInfo(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("files");
	}
	
	
	
}
