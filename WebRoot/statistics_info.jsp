<%@ page language="java" import="java.util.*,com.liaoyb.po.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'statistics_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    当前在线人数:${numSessions}<br/>
    <%
    Map<HttpSession,String >users= ( Map<HttpSession,String >)application.getAttribute("users");
    if(users!=null){
    int usercount=users.keySet().size();
    %>
    已登陆用户:<%=usercount %></br>
    <%
    
	for(HttpSession se:users.keySet()){
		User user=(User)se.getAttribute("user");
		out.print(user.getUsername()+"</br>");
	}
    }
    %>
    
  </body>
</html>
