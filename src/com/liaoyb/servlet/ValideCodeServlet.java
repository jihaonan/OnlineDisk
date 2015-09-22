package com.liaoyb.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liaoyb.util.VerifyCode;

@WebServlet("/ValideCodeServlet")
public class ValideCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ServletOutputStream sos = response.getOutputStream();
		
		VerifyCode vc = new VerifyCode();
		
		BufferedImage bi = vc.getImage();
		
		String verifyCode = vc.getText();
		
		session.setAttribute("sessionVerifyCode", verifyCode);
		
		VerifyCode.output(bi, sos);
	}
}
