package com.liaoyb.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;

public class DownloadServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		Object objUser=request.getSession().getAttribute("user");
		User user=null;
		if(objUser==null){
			/* 设置格式为text/json */
			response.setContentType("text/json");
			/* 设置字符集为'UTF-8' */
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			//当前未登录，跳转到登陆页面
			System.out.println("当前用户未登录");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			out.print(Json.writeMess("当前用户未登录"));
			return;
		}
		
		user=(User) objUser;
		
	

		MyFile myFile=null;
		request.setCharacterEncoding("utf-8");
		String data_id=request.getParameter("data_id");
		String share_id=request.getParameter("share_id");
		System.out.println(data_id);
		if(data_id!=null){
			//用户自己的文件
			//data_id和是当前user的,不然不给下载
			myFile=diskServ.downloadMySelf(user,data_id);
			//发生错误
			if(myFile==null){
				//转发到错误页面
				request.setAttribute("error", "下载请求错误");
				System.out.println("下载错误");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				
				return ;
			}
			
			
		}
		if(share_id!=null){
			//通过分享下载
			//是分享的share_id
			
			
			
			
		}
		
		
		String fileUrl=myFile.getSourceFile().getFileUrl();
		
		
		response.setContentType("application/x-msdownload");
		// String
		// str="attachment;filename="+URLEncoder.encode(fileName,"utf-8");
//		String str = "attachment;filename="
//				+ new String(fileName.getBytes("utf-8"), "iso8859-1");
		//解决下载中文名乱码问题
		String str="attachment;filename="+encodeFileName(request,myFile.getFileName());
		response.setHeader("Content-Disposition", str);
		/*
		 * servlet实现下载时，如果客户端安装了迅雷，发现不能正确下载。
		 * 查找原因后发现是http头设置的问题，如文件类型CONTEN-TYPE、文件长度CONTEN-LENGTH。
		 * response.setContentLength(fileSize);
		 * response.setContentType(contentType);
		 */
		if(!new File(fileUrl).exists()){
			System.out.println("文件不存在");
			//转发到错误页面
			
			request.setAttribute("error", "下载文件出错了");
			request.getRequestDispatcher(this.getServletContext().getContextPath()+"/error.jsp").forward(request, response);
			return;
		}
		
		
		InputStream is = new FileInputStream(fileUrl);
		ServletOutputStream sos = response.getOutputStream();
		byte[] data = new byte[2048];
		int len = 0;
		while ((len = is.read(data)) > 0) {
			sos.write(data, 0, len);
		}
		System.out.println("下载完毕");
		sos.close();
		is.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}
	
	//解决不同浏览器下载中文名乱码问题
		private  String encodeFileName(HttpServletRequest req, String name)
				throws UnsupportedEncodingException {

			String agent = req.getHeader("USER-AGENT").toLowerCase();
			System.out.println(agent);
			if (agent != null && agent.indexOf("firefox") < 0
					&& agent.indexOf("safari") < 0) {
				return URLEncoder.encode(name, "UTF8");
			}
			
			return new String(name.getBytes("UTF-8"), "ISO8859-1");

		}

}
