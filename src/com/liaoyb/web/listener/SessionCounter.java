package com.liaoyb.web.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 监听器，
 * @author Liao-Pc
 *当一个浏览器第一次访问网站的时候，J2EE应用服务器会新建一个HttpSession对象 ，
 *并触发 HttpSession创建事件 ，如果注册了HttpSessionListener事件监听器，
 *则会调用HttpSessionListener事件监听器的sessionCreated方法。相反，
 *当这个浏览器访问结束超时的时候，J2EE应用服务器会销毁相应的HttpSession对象，
 *触发 HttpSession销毁事件，
 *同时调用所注册HttpSessionListener事件监听器的sessionDestroyed方法。
 *
 *
 */
public class SessionCounter implements HttpSessionListener {
	//Session创建事件
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		ServletContext servCont=event.getSession().getServletContext();
		Integer numSessions=(Integer)servCont.getAttribute("numSessions");
		if(numSessions==null){
			numSessions=1;
		}else{
			int count=numSessions.intValue();
			numSessions=++count;
		}
		servCont.setAttribute("numSessions", numSessions);
		System.out.println("有新用户访问");

	}
	
	
	//Session失效事件
	//把失效的用户移除
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext servCont=se.getSession().getServletContext();
		Integer numSessions=(Integer) servCont.getAttribute("numSessions");
		if(numSessions==null){
			numSessions=0;
		}else{
			int count=numSessions.intValue();
			numSessions=count-1;
		}
		servCont.setAttribute("numSessions", numSessions);
		
		//
		 Map<HttpSession,String >users=(Map<HttpSession, String>) servCont.getAttribute("users");
		 if(users!=null){
			 users.remove(se.getSession());
		 }
		
		System.out.println("有用户离开");
	}

}
