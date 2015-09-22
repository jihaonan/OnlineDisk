package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;
import com.liaoyb.util.ServletUtil;

public class DeleteFileServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=ServletUtil.checkUserLogMess(request, response);
		if(user==null)
			return;
		// 拿到传过来的data_id,
		request.setCharacterEncoding("utf-8");
		String data_id = request.getParameter("data_id");
		System.out.println("要删除的data_id"+data_id);
	
		//如果是文件夹，要把在文件夹下的都删除
		
		
		diskServ.delete(data_id);
		//把在session中的files清空
		request.getSession().removeAttribute("files");
		
		
		response.setContentType("text/json");
		/* 设置字符集为'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(Json.writeMess("删除成功"));
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
