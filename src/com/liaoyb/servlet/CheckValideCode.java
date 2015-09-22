package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liaoyb.util.Json;
/**
 * 对验证码进行验证
 * 返回json数据
 * @author Liao-Pc
 *
 */
public class CheckValideCode extends HttpServlet {

//返回状态信息,    "0"---表示验证失败,      "200"---成功
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String valicodepar=request.getParameter("verifyCode");
		
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(valicodepar==""||valicodepar.trim()==""){
			//输入的验证码为空
			out.print(Json.writeLogState("0"));
			
		}
		
		String valicode=(String) request.getSession().getAttribute("sessionVerifyCode");
		if(valicode!=null&&valicode.equals(valicodepar)){
			//验证码正确
			
			out.print(Json.writeLogState("200"));
		}else{
			out.print(Json.writeLogState("0"));
		}
		
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
