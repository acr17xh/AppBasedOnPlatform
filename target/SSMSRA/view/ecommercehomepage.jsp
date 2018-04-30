<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<!-- SITE TITTLE -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sheffield Secondhank Book Flea Market</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- PLUGINS CSS STYLE -->
<link href="${contextPath}/plugins/jquery-ui/jquery-ui.min.css"
	rel="stylesheet">
<!-- Bootstrap -->
<link href="${contextPath}/plugins/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="${contextPath}/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- Owl Carousel -->
<link href="${contextPath}/plugins/slick-carousel/slick/slick.css"
	rel="stylesheet">
<link href="${contextPath}/plugins/slick-carousel/slick/slick-theme.css"
	rel="stylesheet">
<!-- Fancy Box -->
<link href="${contextPath}/plugins/fancybox/jquery.fancybox.pack.css"
	rel="stylesheet">
<link
	href="${contextPath}/plugins/jquery-nice-select/css/nice-select.css"
	rel="stylesheet">
<link
	href="${contextPath}/plugins/seiyria-bootstrap-slider/dist/css/bootstrap-slider.min.css"
	rel="stylesheet">
<!-- CUSTOM CSS -->
<link href="${contextPath}/css/style.css" rel="stylesheet">

<!-- FAVICON -->
<!--<link href="${contextPath}/img/favicon.png" rel="shortcut icon">  -->
<!-- JAVASCRIPTS -->
<script src="${contextPath}/plugins/jquery/jquery.min.js"></script>
<script src="${contextPath}/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${contextPath}/plugins/tether/js/tether.min.js"></script>
<script src="${contextPath}/plugins/raty/jquery.raty-fa.js"></script>
<script src="${contextPath}/plugins/bootstrap/dist/js/popper.min.js"></script>
<script src="${contextPath}/plugins/bootstrap/dist/js/bootstrap.min.js"></script>
<script
	src="${contextPath}/plugins/seiyria-bootstrap-slider/dist/bootstrap-slider.min.js"></script>
<script src="${contextPath}/plugins/slick-carousel/slick/slick.min.js"></script>
<script
	src="${contextPath}/plugins/jquery-nice-select/js/jquery.nice-select.min.js"></script>
<script src="${contextPath}/plugins/fancybox/jquery.fancybox.pack.js"></script>
<script src="${contextPath}/plugins/smoothscroll/SmoothScroll.min.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCC72vZw-6tGqFyRhhg5CkF2fqfILn2Tsw"></script>

</head>
<body class="body-wrapper">
	<section>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<nav class="navbar navbar-expand-lg  navigation">
						<a class="navbar-brand" href=""> <img
							src="${contextPath}/images/logo.png" alt="">
						</a>
						<button class="navbar-toggler" type="button"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<span class="navbar-toggler-icon"></span>
						</button>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav ml-auto main-nav ">
								<li class="nav-item active"><a class="nav-link"
									href="${contextPath}/home/home">Home</a></li>
								<li class="nav-item dropdown dropdown-slide"><a
									class="nav-link dropdown-toggle" href="#"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">Experimental<span><i
											class="fa fa-angle-down"></i></span>
								</a>
									<div class="dropdown-menu dropdown-menu-right">
										<a class="dropdown-item"
											href="${contextPath}/redpacket/redpacketpage">Grab Red
											Packet!</a>
									</div></li>

							</ul>
							<shiro:guest>
								<ul class="navbar-nav ml-auto mt-10">
									<li class="nav-item"><a class="nav-link login-button"
										href="${contextPath}/home/loginpage">Login</a></li>
								</ul>
							</shiro:guest>
							<shiro:user>
								<ul class="navbar-nav ml-auto mt-10">
									<li class="nav-item"><a class="nav-link login-button"
										href="${contextPath}/user/peanutbank">Penaut Bank</a></li>
									<li class="nav-item"><a class="nav-link login-button"
										href="${contextPath}/user/userdashboard">My Account</a></li>
									<li class="nav-item"><a class="nav-link login-button"
										href="${contextPath}/user/logout">Logout</a></li>
									<li class="nav-item"><a class="nav-link add-button"
										href="${contextPath}/buyer/shoppingcart/newcommodity"><i
											class="fa fa-plus-circle"></i>Shopping Cart</a></li>
								</ul>
							</shiro:user>
						</div>
					</nav>
				</div>
			</div>
		</div>
	</section>

	<!--===============================
=            Hero Area            =
================================-->

	<section class="hero-area bg-1 text-center overly">
		<!-- Container Start -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<!-- Header Contetnt -->
					<div class="content-block">
						<h1>Buy & Sell Near You</h1>
						<p>
							Join the millions who buy and sell from each other <br>
							everyday in local communities around the world
						</p>
						<div class="short-popular-category-list text-center">
							<h2>Popular Category</h2>
							<ul class="list-inline">
								<li class="list-inline-item"><a
									href="${contextPath}/search/searchforstore?searchcontent=Computer%20Science"><i
										class="fa fa-bed"></i> Computer Science</a></li>
								<li class="list-inline-item"><a
									href="${contextPath}/search/searchforstore?searchcontent=Novel"><i
										class="fa fa-grav"></i> Novel</a></li>
								<li class="list-inline-item"><a
									href="${contextPath}/search/searchforstore?searchcontent=Economics"><i
										class="fa fa-car"></i> Economics</a></li>
								<li class="list-inline-item"><a
									href="${contextPath}/search/searchforstore?searchcontent=TextBook"><i
										class="fa fa-cutlery"></i> TextBook</a></li>
								<li class="list-inline-item"><a
									href="${contextPath}/search/searchforstore?searchcontent=Psychology"><i
										class="fa fa-coffee"></i> Psychology</a></li>
							</ul>
						</div>
					</div>
					<!-- Advance Search -->
					<!-- 整个输入框，减少用户操作 -->
					<div class="advance-search">
						<form action="${contextPath}/search/searchforstore">
							<div class="row">
								<!-- Store Search -->
								<div class="col-lg-12 col-md-12">
									<div class="block d-flex">
										<input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
											id="search" name="searchcontent"
											placeholder="Search for store">
										<button type="submit" class="btn btn-main">SEARCH</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- Container End -->
	</section>

	<!--===================================
=            Client Slider            =
====================================-->


	<!--===========================================
=            Popular deals section            =
============================================-->

	<section class="popular-deals section bg-gray">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="section-title">
						<h2>Some popular books</h2>
						<p>Randomly recommend some books.  Currently we have
							${commoditiesnumber} commodities.</p>
					</div>
				</div>
			</div>
			<div class="row">
				<c:forEach items="${commoditylist}" var="commodity">
					<div class="col-sm-12 col-lg-4">

						<!-- product card -->
						<div class="product-item bg-light">
							<div class="card">
								<div class="thumb-content">
									<a
										href="${contextPath}/buyer/commodity?commodityid=${commodity.commodityid}">
										<img class="card-img-top img-fluid" width="420px"
										height="275px"
										src="${contextPath}/search/commodityimage/${commodity.commodityid}"
										alt="Card image cap">
									</a>
								</div>
								<div class="card-body">
									<h4 class="card-title">
										<a
											href="${contextPath}/buyer/commodity?commodityid=${commodity.commodityid}">${commodity.commodityname}</a>
									</h4>
									<ul class="list-inline product-meta">
										<li class="list-inline-item"><a
											href="${contextPath}/buyer/commodity?commodityid=${commodity.commodityid}"><i
												class="fa fa-folder-open-o"></i>${commodity.commoditycategory}</a></li>
										<li class="list-inline-item"><a
											href="${contextPath}/buyer/commodity?commodityid=${commodity.commodityid}"><i
												class="fa fa-calendar"></i>${commodity.commoditydate}</a></li>
									</ul>
									<ul>
										<li><p class="card-text">${commodity.commoditydescription}</p></li>
										<li><p class="card-text">Price:
												${commodity.commodityprice}</p></li>
									</ul>
									<!-- 星级评价 -->
									<div class="product-ratings">
										<ul class="list-inline">
											<c:choose>
												<c:when test="${commodity.rate!=0}">
													<c:forEach var="i" begin="1" end="${commodity.rate}">
														<li class="list-inline-item selected"><i
															class="fa fa-star"></i></li>
													</c:forEach>
												</c:when>
												<c:when test="${commodity.rate==0}">
													<li><p class="card-text">Not yet rated.</p></li>
												</c:when>
											</c:choose>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>

	<section class=" section">
		<!-- Container Start -->
		<div class="container">
			<div class="row">
				<div class="col-12">
					<!-- Section title -->
					<div class="section-title">
						<h2>All Categories</h2>
						<p>View commodities by clicking categories.</p>
					</div>
					<div class="row">
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-laptop icon-bg-1"></i>
									<h4>Literature</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Novel">Novel</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Biography">Biography</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Poetrye">Poetry</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Prose">Prose</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-apple icon-bg-2"></i>
									<h4>Education</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Novel">Textbook</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Exam">Exam</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Dictionary">Dictionary</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Notes">Notes</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-home icon-bg-3"></i>
									<h4>Finance</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Economy">Economy</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Management">Management</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Accounting">Accounting</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Marketing">Marketing</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-shopping-basket icon-bg-4"></i>
									<h4>Social Sciences</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Politics">Politics</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=History">History</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Psychology">Psychology</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Journalism">Journalism</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-briefcase icon-bg-5"></i>
									<h4>Life</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Fitness">Fitness</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Tourism">Tourism</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Cooking">Cooking</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Home">Home</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-car icon-bg-6"></i>
									<h4>EEE and CS</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Electronics">Electronics</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Electrical">Electrical</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Telecommunication">Telecommunication</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Computer%20Science">Computer
											Science</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-paw icon-bg-7"></i>
									<h4>Engineering</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Natural%20Science">Natural
											Science</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Civil%20Engneering">Civil
											Engineering</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Biography">Biography</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Architecture">Architecture</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->
						<!-- Category list -->
						<div
							class="col-lg-3 offset-lg-0 col-md-5 offset-md-1 col-sm-6 col-6">
							<div class="category-block">
								<div class="header">
									<i class="fa fa-laptop icon-bg-8"></i>
									<h4>Foreign Books</h4>
								</div>
								<ul class="category-list">
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=French">French</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=German">German</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Italian">Italian</a></li>
									<li><a
										href="${contextPath}/search/searchforstore?searchcontent=Chinese">Chinese</a></li>
								</ul>
							</div>
						</div>
						<!-- /Category List -->


					</div>
				</div>
			</div>
		</div>
		<!-- Container End -->
	</section>

	<!--============================
=            Footer            =
=============================-->

	<footer class="footer section section-sm">
		<!-- Container Start -->
		<div class="container">
			<div class="row">
				<div class="col-lg-3 col-md-7 offset-md-1 offset-lg-0">
					<!-- About -->
					<div class="block about">
						<!-- footer logo -->
						<img src="${contextPath}/images/logo-footer.png" alt="">
						<!-- description -->
						<p class="alt-color">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit, sed do eiusmod tempor incididunt ut labore et
							dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
							exercitation ullamco laboris nisi ut aliquip ex ea commodo
							consequat.</p>
					</div>
				</div>
				<!-- Link list -->
				<div class="col-lg-2 offset-lg-1 col-md-3">
					<div class="block">
						<h4>Site Pages</h4>
						<ul>
							<li><a href="#">Boston</a></li>
							<li><a href="#">How It works</a></li>
							<li><a href="#">Deals & Coupons</a></li>
							<li><a href="#">Articls & Tips</a></li>
							<li><a href="#">Terms of Services</a></li>
						</ul>
					</div>
				</div>
				<!-- Link list -->
				<div class="col-lg-2 col-md-3 offset-md-1 offset-lg-0">
					<div class="block">
						<h4>Admin Pages</h4>
						<ul>
							<li><a href="#">Boston</a></li>
							<li><a href="#">How It works</a></li>
							<li><a href="#">Deals & Coupons</a></li>
							<li><a href="#">Articls & Tips</a></li>
							<li><a href="#">Terms of Services</a></li>
						</ul>
					</div>
				</div>
				<!-- Promotion -->
				<div class="col-lg-4 col-md-7">
					<!-- App promotion -->
					<div class="block-2 app-promotion">
						<a href=""> <!-- Icon --> <img
							src="${contextPath}/images/footer/phone-icon.png"
							alt="mobile-icon">
						</a>
						<p>Get the Dealsy Mobile App and Save more</p>
					</div>
				</div>
			</div>
		</div>
		<!-- Container End -->
	</footer>
	<!-- Footer Bottom -->
	<footer class="footer-bottom">
		<!-- Container Start -->
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-12">
					<!-- Copyright -->
					<div class="copyright">
						<p>Copyright © 2016. All Rights Reserved</p>
					</div>
				</div>
				<div class="col-sm-6 col-12">
					<!-- Social Icons -->
					<ul class="social-media-icons text-right">
						<li><a class="fa fa-facebook" href=""></a></li>
						<li><a class="fa fa-twitter" href=""></a></li>
						<li><a class="fa fa-pinterest-p" href=""></a></li>
						<li><a class="fa fa-vimeo" href=""></a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- Container End -->
		<!-- To Top -->
		<div class="top-to">
			<a id="top" class="" href=""><i class="fa fa-angle-up"></i></a>
		</div>
	</footer>



</body>
</html>