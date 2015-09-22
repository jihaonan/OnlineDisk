<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<title>Register</title>
<link rel="stylesheet" href="css/login.css">
 <link rel="shortcut icon" href="img/logo.ico" type="image/x-icon" />
</head>

<body >
	<!-- 为页面添加表单校验 -->
	<script type="text/javascript" src="http://libs.baidu.com/jquery/2.0.3/jquery.min.js"></script>
	<script type="text/javascript">
	
		// 切换验证码图片
		function nextImage(){
			var newImg = document.getElementById("verifyCodeID");
			newImg.src = "ValideCodeServlet?t=" + new Date().getTime();
		}
		
		$(function(){
 			
			// 阻止表单的默认行为
			var flag = false;
			// 校验用户名
			$("#submit").click(function(){
				var username = $("#username").val();
				var password = $("#password").val();
				var repassword = $("#repassword").val();
				var vCode = $("#verifyCode").val();
				if (username == "" || username.trim() == "") {
					alert("用户名不能为空！");
					return flag;
				} else if(username.length < 2 || username.length > 7) {
					alert("用户名必须在2~7个字符之间！");
					return flag;
				} else if (password == "" || password.trim() == "") {
					alert("密码不能为空！");
					return flag;
				} else if(password.length < 6 || password.length > 12) {
					alert("密码必须在6~12个字符之间！");
					return flag;
				} else if(repassword != password) {
					alert("两次密码不匹配，请重新输入！");
					return flag;
				} else if (vCode == "" || vCode.trim() == "") {
					alert("请输入验证码！");
					return flag;					
				} else if (vCode.length != 4) {
					alert("验证码必须是4个字符！");
					return flag;					
				}else if($("#info").text()!="Register"){
					return flag;
				} else {
					flag = true;
					return flag;
				}
			});
			// 如果验证失败，则不提交表单
			$("#form1").submit(function(){
				return flag;
			});
		})
	</script>
	<section class="container">
		<div class="login">
			<h1 id="info">Register</h1>
			<a hidden="hidden" id="urlpath">${pageContext.request.contextPath}</a>
			  ${param.message}
			<form method="post" action="${pageContext.request.contextPath}/servlet/RegisterServlet" id="form1">
				<p>
					<input type="text" name="username" id="username" value=""
						placeholder="Input Username">
				</p>
				<p>
					<input type="password" name="password" id="password" value=""
						placeholder="Input Password">
				</p>
				<p>
					<input type="password" name="repassword" id="repassword" value=""
						placeholder="Please Sure Password">
				</p>
				<p class="vCode">
					<input type="text" name="verifyCode" id="verifyCode" style="width:90px;" placeholder="Input Image"><img src="${pageContext.request.contextPath}/ValideCodeServlet" id="verifyCodeID"></img><a href="javascript:nextImage()">NEXT</a>
				</p>

				<p class="submit">
					<input type="reset" name="reset" id="reset" value="ESC">
					<input type="submit" name="commit" id="submit"value="OK">
				</p>
			</form>
		</div>


	</section>

	<div style="text-align:center;clear:both;"></div>
</body>
<script>
	//用户名
	$("#username").change(function(){
		var username=$(this).val();
		console.log("username="+username);
		var cheurl=$("#urlpath").text()+"/servlet/CheckUserExit";
		//发送请求
		$.ajax({
			url:cheurl,
			type:'post',
			data:"username="+username,
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
					//验证失败
					$("#info").text("用户名已存在");

				}else if(state=="200"){
					//成功

					$("#info").text("Register");
				}

			}
		});


	});



//验证码
	$("#verifyCode").change(function(){
		var verifyCode=$(this).val();
		console.log("verifyCode="+verifyCode);
		var cheurl=$("#urlpath").text()+"/servlet/CheckValideCode";
		//发送请求
		$.ajax({
			url:cheurl,
			type:'post',
			data:"verifyCode="+verifyCode,
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
					//验证失败
					if($("#info").text()!="Register"){
						var ortext=$("#info").text();
						$("#info").text(ortext+",验证码错误");
					}else{
						$("#info").text("验证码错误");
					}
					

				}else if(state=="200"){
					//成功

					$("#info").text("Register");
				}

			}
		});


	});




</script>



</html>