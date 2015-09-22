package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.util.ServletUtil;

public class LogoutServlet extends HttpServlet {


	//注销当前用户,跳转到首页
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//清除登陆信息
		ServletUtil.clearLoginInfo(request);
		//跳转到首页
		response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
		
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

}
