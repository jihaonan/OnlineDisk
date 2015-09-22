package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.util.IpUtil;


public class GainIp extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ipaddr="";
		try {
		 ipaddr=IpUtil.getIpAddr(request);
			
			System.out.println("ipaddr:"+ipaddr);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("ipaddr:"+ipaddr+"<br>");
		try {
			out.print("ipfirst:"+IpUtil.getFirstIP(request)+"<br>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	out.print("request.getRemoteAddr():"+request.getRemoteAddr()+"</br>");
	out.print("request.getRemotePort():"+request.getRemotePort()+"</br>");

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	
	}

}
