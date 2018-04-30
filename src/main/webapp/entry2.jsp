<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>Our apps</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/blog/bootstrap.min.css">
<!-- Theme CSS -->
<link href="${contextPath}/blog/clean-blog.min.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="${contextPath}/blog/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link
	href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<script src="${contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${contextPath}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!-- Contact Form JavaScript -->
<script src="${contextPath}/blog/jqBootstrapValidation.js"></script>
<script src="${contextPath}/blog/contact_me.js"></script>
<!-- Theme JavaScript -->
<script src="${contextPath}/blog/clean-blog.min.js"></script>
<script type="text/javascript">
	<script>
	document.oncontextmenu = function() {
		return false;
	}//禁止右键
	document.onkeydown = function(e) {//键盘按键控制
		e = e || window.event;
		if ((e.ctrlKey && e.keyCode == 82) || //ctrl+R
		e.keyCode == 116) {//F5刷新，禁止
			//阻止默认刷新时间
			e.returnValue = false
			if (e.preventDefault)
				e.preventDefault();
			else
				e.keyCode = 0;
			//你的刷新IFrame代码
			return false;
		}
	}
	window.onbeforeunload = function(e) {
		return (e || window.event).returnValue = 'Leave this page?';
	}
</script>
</head>
<body>
	<!-- Page Header -->
	<!-- Set your background image for this header on the line below. -->
	<header class="intro-header"
		style="background-image: url('${contextPath}/images/home-bg.jpg')">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
					<div class="site-heading">
						<h1>Simple introduction</h1>
						<hr class="small">
						<h2>
							<span class="subheading">Choose one app then login. </span>
						</h2>
						<h4>P.S. 'logout' button in each app redirect to this page.
							To logout (Invalidate your session), please click red 'LOGOUT' button
							below.</h4>
					</div>
				</div>
			</div>
		</div>
	</header>
	<shiro:user>
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
					<div class="post-preview">
						<a href="${contextPath}/home/turnback?choose=1">
							<h2 class="post-title">Sheffield University Second-hand Book
								Flea Market</h2>
							<h4 class="post-subtitle">This website provides the
								following functions: Register/Login/Logout; Update profile;
								Sell/Manage/Buy commodities with peanuts; Create/Delete
								addresses; Browse commodities by searching or sorted by
								attribute; Send/Grab red packets using Redis.Issues about
								transaction/security/middleware/NoSQL are considered. Currently
								this project used Spring, Mybatis, MySQL, JQuery, Bootstrap,
								Apache Shiro, ActiveMQ, Redis.</h4>
						</a>
					</div>
					<hr>
					<div class="post-preview">
						<a href="${contextPath}/home/turnback?choose=2">
							<h2 class="post-title">APP 2</h2>
							<h4 class="post-subtitle">Description for APP 2</h4>
						</a>
					</div>
					<hr>
				</div>
			</div>
		</div>
		<hr>
	</shiro:user>
	<shiro:guest>
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
					<div class="post-preview">
						<a href="${contextPath}/home/tologin?choose=1">
							<h2 class="post-title">Sheffield University Second-hand Book
								Flea Market</h2>
							<h3 class="post-subtitle">This website provides the
								following functions: Register/Login/Logout; Update profile;
								Sell/Manage/Buy commodities with peanuts; Create/Delete
								addresses; Browse commodities by searching or sorted by
								attribute; Send/Grab red packets using Redis.Issues about
								transaction/security/middleware/NoSQL are considered. Currently
								this project used Spring, Mybatis, MySQL, JQuery, Bootstrap,
								Apache Shiro, ActiveMQ, Redis.</h3>
						</a>
					</div>
					<hr>
					<div class="post-preview">
						<a href="${contextPath}/home/tologin?choose=2">
							<h2 class="post-title">APP 2</h2>
							<h3 class="post-subtitle">Description for APP 2</h3>
						</a>
					</div>
					<hr>
				</div>
			</div>
		</div>
		<hr>
	</shiro:guest>
	<shiro:user>
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="row clearfix">
						<div class="col-md-4 column"></div>
						<div class="col-md-4 column">
							<form action="${contextPath}/user/logout2">
								<button type="submit"
									class="btn btn-default btn-block btn-danger">Logout</button>
							</form>
						</div>
						<div class="col-md-4 column"></div>
					</div>
				</div>
			</div>
		</div>
	</shiro:user>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<footer class="py-5 bg-dark">
					<div class="container">
						<p class="m-0 text-center text-white">Copyright &copy; Our
							Team 2018</p>
					</div>
				</footer>
			</div>
		</div>
	</div>
</body>
</html>
