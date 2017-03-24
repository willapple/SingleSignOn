<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName()
      + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>系统登录页面</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EDGE">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<style type="text/css">
body {
	margin: auto;
	padding: 0 auto;
	width: 1000px;
	height: 666px;
	background-color: #9DA0D9;
	background-repeat: no-repeat;
}

#main {
	width: 100%;
	height: 100%;
	<%-- background-image: url("<%=basePath%>images/bg.png"); --%>
}
</style>
</head>

<body>
	<div id="main" style="margin:auto;">
		<form action="<%=basePath%>user/loginTest" method="POST"
			id="login1">
			<input type="text" name="username" placeholder="用户名"
				style="position:absolute;margin:280px auto auto 330px;width:333px; height:35px; border:solid 1px #d1d1d1;">
			<input type="password" name="password"  placeholder="密码"
				style="position:absolute;margin:330px auto auto 330px;width:333px; height:35px; border:solid 1px #d1d1d1;">
			<input type="image" src="<%=basePath%>images/btn.png"
				style="position:absolute;margin:400px auto auto 330px;width:337px;height:35px;border:none">
		</form>
	</div>
</body>

</html>
