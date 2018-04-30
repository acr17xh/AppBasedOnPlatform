<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Entry Page</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- <link rel="stylesheet" type="text/css"
	href="${contextPath}/js/bootstrap-3.3.7-dist/css/bootstrap.min.css"> -->
<!--  <script src="${contextPath}/js/jquery-3.2.1.min.js"></script>-->
<script src="${contextPath}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="${contextPath}/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="${contextPath}/css/scrolling-nav.css" rel="stylesheet">
</head>
<body id="page-top">
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand js-scroll-trigger" href="#page-top">Start
				Bootstrap</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="#about">About</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="#services">Services</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="#contact">Contact</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="#contact2">Contact2</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<header class="bg-primary text-white">
		<div class="container text-center">
			<h1>Welcome to Scrolling Nav</h1>
			<p class="lead">A landing page template freshly redesigned for
				Bootstrap 4</p>
		</div>
	</header>

	<section id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mx-auto">
					<h2>About this page</h2>
					<p class="lead">This is a great place to talk about your
						webpage. This template is purposefully unstyled so you can use it
						as a boilerplate or starting point for you own landing page
						designs! This template features:</p>
					<ul>
						<li>Clickable nav links that smooth scroll to page sections</li>
						<li>Responsive behavior when clicking nav links perfect for a
							one page website</li>
						<li>Bootstrap's scrollspy feature which highlights which
							section of the page you're on in the navbar</li>
						<li>Minimal custom CSS so you are free to explore your own
							unique design options</li>
					</ul>
					<form action="${contextPath}/user/firstpage" method="post"
						role="form">
						<button type="submit" class="btn btn-block btn-primary btn-lg">Sheffield
							Secondhand Book Flea Market</button>
					</form>
				</div>
			</div>
		</div>
		</div>
	</section>

	<section id="services" class="bg-light">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mx-auto">
					<h2>Services we offer</h2>
					<p class="lead">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Aut optio velit inventore, expedita quo
						laboriosam possimus ea consequatur vitae, doloribus consequuntur
						ex. Nemo assumenda laborum vel, labore ut velit dignissimos.</p>
				</div>
			</div>
		</div>
	</section>

	<section id="contact">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mx-auto">
					<h2>Contact us</h2>
					<p class="lead">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Vero odio fugiat voluptatem dolor, provident
						officiis, id iusto! Obcaecati incidunt, qui nihil beatae magnam et
						repudiandae ipsa exercitationem, in, quo totam.</p>
				</div>
			</div>
		</div>
	</section>

	<section id="contact2">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mx-auto">
					<h2>Contact us</h2>
					<p class="lead">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Vero odio fugiat voluptatem dolor, provident
						officiis, id iusto! Obcaecati incidunt, qui nihil beatae magnam et
						repudiandae ipsa exercitationem, in, quo totam.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your
				Website 2017</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="${contextPath}/vendor/jquery/jquery.min.js"></script>
	<script
		src="${contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Plugin JavaScript -->
	<script src="${contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom JavaScript for this theme -->
	<script src="${contextPath}/js/scrolling-nav.js"></script>

</body>
</html>
