package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.service.IUserService;
import com.liaoyb.service.impl.UserService;
import com.liaoyb.util.Json;
/**
 * 浏览器异步请求，用户名是否已经注册过了
 * 回送json数据,   
 * @author Liao-Pc
 *
 */
public class CheckUserExit extends HttpServlet {

	private IUserService userServ=new UserService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		
		
		boolean isExit=userServ.userExitByName(username);
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(isExit){
			//用户名存在,   返回json    "0"--用户名存在,    "200"---
		
			out.print(Json.writeLogState("0"));
		}else{
			out.print(Json.writeLogState("200"));
		}
		
		
		
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	
	}

}
