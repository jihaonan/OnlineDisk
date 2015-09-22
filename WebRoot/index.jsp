<%@ page language="java" import="java.util.*,com.liaoyb.po.User" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MyWorld</title>
    <link rel="shortcut icon" href="img/logo.ico" type="image/x-icon" />
</head>
<link rel="stylesheet" href="css/bootstrap.css">
<body>
<a id="contextPath" hidden="hidden">${pageContext.request.contextPath}</a>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
               <li ><a id="a_user" class="dropdown-toggle" data-toggle="dropdown"  role="button" style="display: none;"><strong>${user.username}</strong></a>
                    <ul class="dropdown-menu " id="dr">
                        <li><a href="userinfo.jsp">个人信息</a></li>
                        <li><a href="${pageContext.request.contextPath}/disk.jsp" role="button">我的网盘</a></li>
                        <li><a href="${pageContext.request.contextPath}/servlet/LogoutServlet">注销</a></li>

                    </ul>
                </li>
                
                <li><a id="a_login" href="${pageContext.request.contextPath}/login.jsp" role="button" style="display: none;"><strong>登陆</strong></a></li>
                <li><a href="${pageContext.request.contextPath}/register.jsp" role="button"><strong>注册</strong></a></li>
				<li><a href="leave_message.html" role="button"><strong>留言</strong></a></li>
                <li><a href="update_log.html" role="button"><strong>日志</strong></a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="jumbotron">
    <h1>Welcome to My World</h1>
    <p><a class="btn btn-primary btn-lg" role="button" href="#">Learn more</a></p>
</div>
<div class="page-header">
<h1>以下是最新资讯</h1>
    <div class="row">
        <div class="col-sm-6 col-md-3" onclick="javascript:location.href='index.jsp'">
            <div class="thumbnail">
                <img src="img/img.jpg" alt="..." role="button" >
                <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. </p>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img src="img/img2.jpg" alt="..." role="button" >
                <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. </p>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img src="img/img3.jpg" alt="..." role="button" >
                <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. </p>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img src="img/img4.png" alt="..." role="button" >
                <div class="caption">
                    <h3>Thumbnail label</h3>
                    <p>Cras justo odio </p>
                </div>
            </div>
        </div>
    </div>
</div>






<script src="js/jquery-2.1.4.js"></script>
<script src="js/bootstrap.js"></script>




<script>
    if($("#a_user strong").text().trim()==""){
        $("#a_login").css("display","block");
    }else{
        $("#a_user").css("display","block");
    }

if( $("#a_login").css("display")=="none"){
    //每1分钟执行一次
    var  timer=window.setInterval("checkUserState()",60000);
}
function checkUserState(){
    var url=$("#contextPath").text()+"/servlet/CheckUserState";
    //发送请求
    $.ajax({
        url:url,
        type:'post',

        async: true, //默认为true 异步
        error:function(){
            alert('error');
        },
        success:function(data){
            //返回json数据
            var retJson=eval(data);

            //回送的信息
            console.log(retJson.state);
            var state=retJson.state;
            if(state=="0"){
                //登陆失效，或你在别处登录
                alert("登陆失效，或你在别处登录");
                console.log("登陆失效，或你在别处登录");
                 $("#a_login").css("display","block");
                $("#a_user").css("display","none");
                 //清空定时器
                window.clearInterval(timer);
                return false;

            }else if(state=="200"){
                //当前为登录
               return true;

            }

        }
    });
}


</script>



</body>
</html>