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
<script src="${contextPath}/js/jquery-3.2.1.min.js"></script>
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

<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/font-awesome.min.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-*.min.js"></script>

<!-- JAVASCRIPTS UPDATE-->
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

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
									<!--  
									<li class="nav-item"><a class="nav-link add-button"
										href="${contextPath}/redpacket/redpacketpage"><i
											class="fa fa-plus-circle"></i>Grab Red Packet</a></li>-->
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
	
	<section class="page-search">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<!-- Advance Search -->
					<div class="advance-search">
						<form action="${contextPath}/search/searchforstore">
							<div class="form-row">
								<div class="form-group col-md-8">
									<input type="text" class="form-control" id="searchcontent"
										name="searchcontent" placeholder="What are you looking for">
								</div>
								<!--
							<div class="form-group col-md-3">
								<input type="text" class="form-control" id="inputCategory4" placeholder="Category">
							</div>
							<div class="form-group col-md-3">
								<input type="text" class="form-control" id="inputLocation4" placeholder="Location">
							</div>-->
								<div class="form-group col-md-2">

									<button type="submit" class="btn btn-primary">SEARCH</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--===================================
=            Store Section            =
====================================-->
	<section class="section bg-gray">
		<!-- Container Start -->
		<div class="container">
			<div class="row">
				<div class="col-md-8 offset-md-0 col-lg-8 offset-lg-0">
				
					<c:if test="${commoditylist.size()==0}">
						<div class="widget dashboard-container my-adslist">
							<h3 class="widget-header">Transactions successful!</h3>
						</div>
					</c:if>
					<c:if test="${commoditylist.size()!=0}">
						<div class="widget dashboard-container my-adslist"
							id="myshoppingcart">
							<h3 class="widget-header">Failed Transactions</h3>
							<table class="table table-responsive product-dashboard-table">
								<thead>
									<tr>
										<th>Image</th>
										<th>Commodity Title</th>
										<th class="text-center">Commodity Quantity</th>
										<!-- <th class="text-center">Action</th> -->
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${commoditylist}" var="commodity">
										<tr>
											<td class="product-thumb"><img width="80px"
												height="auto"
												src="${contextPath}/user/commodityimage/${commodity.itemid}"
												alt="No image"></td>
											<td class="product-details">
												<h3 class="title">
													<a
														href="${contextPath}/buyer/commodity?commodityid=${commodity.itemid}">${commodity.itemname}</a>
												</h3>
											</td>
											<td class="product-category"><span class="categories">${commodity.itemquantity}</span></td>
											<!-- <td class="action" data-title="Action">
											<div class="">
												<ul class="list-inline justify-content-center">
													<li class="list-inline-item"><a class="delete"
														href="${contextPath}/buyer/shoppingcart/remove?itemid=${commodity.itemid}#myshoppingcart">
															<i class="fa fa-trash"></i>
													</a></li>
												</ul>
											</div>
										</td> -->
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<!-- 分页 -->
					<!-- 购物车页面暂时不考虑分页，参见亚马逊购物车 -->
					<!-- <div class="pagination justify-content-center">
						<nav aria-label="Page navigation example">
							<ul class="pagination">
								<li class="page-item"><a class="page-link"
									href="${contextPath}/user/userdashboard?pageinfo=1"
									aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										<span class="sr-only">First</span>
								</a></li>
								<li class="page-item"><a class="page-link"
									href="${contextPath}/user/userdashboard?pageinfo=${pageinfo.prePage}">Prev</a></li>
								<li class="page-item"><a class="page-link"
									href="${contextPath}/user/userdashboard?pageinfo=${pageinfo.nextPage}">Next</a></li>
								<li class="page-item"><a class="page-link"
									href="${contextPath}/user/userdashboard?pageinfo=${pageinfo.getPages()}"
									aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										<span class="sr-only">Last</span>
								</a></li>
							</ul>
						</nav>
					</div> -->
				</div>
				<div class="col-md-4 offset-md-0 col-lg-4 offset-lg-0">
					<form action="${contextPath}/user/commoditiesbought" class="row">
						<div class="widget disclaimer">
							<h3 class="tab-title">Confirmation</h3>
							<button type="submit"
								class="btn btn-default btn-success btn-block">Orders
								review</button>
						</div>
					</form>
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
						<img src="images/logo-footer.png" alt="">
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
							src="images/footer/phone-icon.png" alt="mobile-icon">
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