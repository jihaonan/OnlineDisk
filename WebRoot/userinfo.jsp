<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        .row a img{
            height: 200px;
        }
    </style>
</head>


<body>


<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading">User   Info</div>
    <div class="panel-body">
    </div>

   <div class="row">
       <div class="col-xs-6 col-md-3">
           <a href="#" class="thumbnail">
               <img src="" >
           </a>
       </div>

   </div>
    <div class="list-group list-inline">
        <a href="#" class="list-group-item ">
            <h4 class="list-group-item-heading">用户名</h4>
            <p class="list-group-item-text">${user.username}</p>

        </a>

    </div>
    <div class="list-group">
        <a href="#" class="list-group-item ">
            <h4 class="list-group-item-heading">昵称</h4>
            <p class="list-group-item-text">${user.nickname}</p>

        </a>
    </div>
    <div class="list-group">
        <a href="#" class="list-group-item ">
            <h4 class="list-group-item-heading">生日</h4>
            <p class="list-group-item-text">${user.birthday}</p>

        </a>
    </div>
    <div class="list-group">
        <a href="#" class="list-group-item ">
            <h4 class="list-group-item-heading">个人简介</h4>
            <p class="list-group-item-text">${user.info}</p>

        </a>
    </div>
</div>

<a href="${pageContext.request.contextPath}/index.jsp" class="col-xs-1 col-lg-offset-6">返回主页</a>

</body>

<script src="js/jquery-2.1.4.js"></script>
<script src="js/bootstrap.js"></script>
</html>