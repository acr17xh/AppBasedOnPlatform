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
								<div class="form-group col-md-2">
									<button type="submit" class="btn btn-primary">Search
										Now</button>
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
				<!-- Left sidebar -->
				<div class="col-md-8">
					<div class="product-details">
						<h3 class="product-title">
							<label><c:out value="${quantityerror}"></c:out></label> <label><c:out
									value="${cannotbuyerror}"></c:out></label>
						</h3>
						<h1 class="product-title">
							<c:out value="${commodity.commodityname}"></c:out>

						</h1>
						<div class="product-meta">
							<ul class="list-inline">
								<li class="list-inline-item"><i class="fa fa-user-o"></i>
									By <a href=""><c:out value="${owner.username}"></c:out></a></li>
								<li class="list-inline-item"><i class="fa fa-folder-open-o"></i>
									Category<a href=""><c:out
											value="${commodity.commoditycategory}"></c:out></a></li>
								<li class="list-inline-item"><i
									class="fa fa-location-arrow"></i> Location<a href=""><c:out
											value="${commodity.commoditylocation}"></c:out></a></li>
							</ul>
						</div>



						<!-- 商品轮播图效果 -->
						<div id="carouselExampleIndicators"
							class="product-slider carousel slide" data-ride="carousel">
							<ol class="carousel-indicators">
								<c:forEach var="slideindex" begin="0"
									end="${imageskeyslistsize}">
									<li data-target="#carouselExampleIndicators"
										data-slide-to="${slideindex}"></li>
								</c:forEach>

							</ol>

							<div class="carousel-inner">
								<div class="carousel-item active">
									<img class="d-block w-100"
										src="${contextPath}/buyer/commodityimages?imgid=${firstimageid}"
										alt="No image">
								</div>
								<c:forEach items="${imageskeyslist}" var="imgid" begin="1">
									<div class="carousel-item">
										<img class="d-block w-100"
											src="${contextPath}/buyer/commodityimages?imgid=${imgid}"
											alt="No image">
									</div>
								</c:forEach>
							</div>
							<a class="carousel-control-prev"
								href="#carouselExampleIndicators" role="button"
								data-slide="prev"> <span class="carousel-control-prev-icon"
								aria-hidden="false"></span> <span class="sr-only">Previous</span>
							</a> <a class="carousel-control-next"
								href="#carouselExampleIndicators" role="button"
								data-slide="next"> <span class="carousel-control-next-icon"
								aria-hidden="false"></span> <span class="sr-only">Next</span>
							</a>
						</div>

						<div class="content">
							<ul class="nav nav-pills  justify-content-center" id="pills-tab"
								role="tablist">
								<li class="nav-item"><a class="nav-link active"
									id="pills-home-tab" data-toggle="pill" href="#pills-home"
									role="tab" aria-controls="pills-home" aria-selected="true">Product
										Description</a></li>
								<li class="nav-item"><a class="nav-link"
									id="pills-profile-tab" data-toggle="pill" href="#pills-profile"
									role="tab" aria-controls="pills-profile" aria-selected="false">Details</a>
								</li>
								<li class="nav-item"><a class="nav-link"
									id="pills-contact-tab" data-toggle="pill" href="#pills-contact"
									role="tab" aria-controls="pills-contact" aria-selected="false">Reviews</a>
								</li>
							</ul>
							<div class="tab-content" id="pills-tabContent">
								<div class="tab-pane fade show active" id="pills-home"
									role="tabpanel" aria-labelledby="pills-home-tab">
									<h3 class="tab-title">Product Description</h3>
									<p>
										<c:out value="${commodity.commoditydescription}"></c:out>
									</p>
									<p></p>
								</div>
								<div class="tab-pane fade show active" id="pills-profile"
									role="tabpanel" aria-labelledby="pills-profile-tab">
									<h3 class="tab-title">Product Specifications</h3>
									<table class="table table-bordered product-table">
										<tbody>
											<tr>
												<td>Seller Price</td>
												<td><c:out value="${commodity.commodityprice}"></c:out></td>
											</tr>
											<tr>
												<td>Added</td>
												<td><c:out value="${commodity.commoditydate}"></c:out></td>
											</tr>
											<tr>
												<td>Sales</td>
												<td><c:out value="${commodity.commoditysales}"></c:out></td>
											</tr>
											<tr>
												<td>Quantity</td>
												<td><c:out value="${commodity.commodityquantity}"></c:out></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="tab-pane fade show active" id="pills-contact"
									role="tabpanel" aria-labelledby="pills-contact-tab">
									<h3 class="tab-title" id="customerreviews">Product Review</h3>
									<div class="product-review">
										<c:forEach var="review" items="${reviewlist}">
											<div class="media">
												<!-- Avater -->
												<!--  <img src="images/user/user-thumb.jpg" alt="avater">-->
												<div class="media-body">
													<!-- Ratings -->
													<div class="ratings">
														<ul class="list-inline">
															<c:forEach var="i" begin="1" end="${review.rate}">
																<li class="list-inline-item selected"><i
																	class="fa fa-star"></i></li>
															</c:forEach>
														</ul>
													</div>
													<div class="name">
														<h5>
															<c:out value="${review.username}"></c:out>
														</h5>
													</div>
													<div class="date">
														<p>
															<c:out value="${review.reviewdate}"></c:out>
														</p>
													</div>
													<div class="review-comment">
														<p>
															<c:out value="${review.customerreview}"></c:out>
														</p>
													</div>
												</div>
											</div>
										</c:forEach>
										<div class="pagination justify-content-center">
											<nav aria-label="Page navigation example">
												<ul class="pagination">
													<li class="page-item"><a class="page-link"
														href="${contextPath}/buyer/commodity?pageinfo2=1&commodityid=${commodity.commodityid}#customerreviews"
														aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
															<span class="sr-only">First</span>
													</a></li>
													<li class="page-item"><a class="page-link"
														href="${contextPath}/buyer/commodity?pageinfo2=${pageinfo2.prePage}&commodityid=${commodity.commodityid}#customerreviews">Prev</a></li>
													<li class="page-item"><a class="page-link"
														href="${contextPath}/buyer/commodity?pageinfo2=${pageinfo2.nextPage}&commodityid=${commodity.commodityid}#customerreviews">Next</a></li>
													<li class="page-item"><a class="page-link"
														href="${contextPath}/buyer/commodity?pageinfo2=${pageinfo2.getPages()}&commodityid=${commodity.commodityid}#customerreviews"
														aria-label="Next"> <span aria-hidden="true">&raquo;</span>
															<span class="sr-only">Last</span>
													</a></li>
												</ul>
											</nav>
										</div>
										<div class="review-submission">
											<h3 class="tab-title">Submit your review</h3>
											<!-- Rate -->
											<!-- 自带评级效果，不知道怎么操作取值,先用下拉框 -->
											<!-- <div class="rate">
												<div class="starrr"></div>
											</div> -->
											<div class="review-submit">
												<form action="${contextPath}/buyer/review#customerreviews"
													class="row">
													<div class="col-6">
														<label>Rate form 1~5:</label>
													</div>
													<div class="col-6">
														<select id="reviewrate" name="reviewrate">
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="4">4</option>
															<option value="5" selected="selected">5</option>
														</select>
													</div>
													<div class="col-lg-6">
														<input type="text" name="reviewname" id="reviewusername"
															class="form-control" value="${currentusername}"
															readonly="readonly">
													</div>
													<div class="col-lg-6">
														<input type="email" name="reviewemail" id="reviewemail"
															class="form-control" placeholder="Your Email">
													</div>

													<div class="col-12">
														<label>You can drag down this textarea (bottom
															right corner).</label>
														<textarea name="reviewtext" id="reviewtext" rows="60"
															class="form-control" placeholder="Your Review"></textarea>
													</div>
													<div class="col-lg-12">
														<input type="hidden" name="reviewcommodityid" id="id"
															class="form-control" value="${commodity.commodityid}">
													</div>
													<c:if test="${reviewerrors.size()!=0}">
														<div class="col-12">
															<c:forEach var="msg" items="${reviewerrors}">
																<label>${msg}</label>
															</c:forEach>
														</div>
													</c:if>
													<div class="col-12">
														<button type="submit" class="btn btn-main">Sumbit</button>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="sidebar">
						<div class="widget price text-center">
							<h4>Price</h4>
							<p>
								<c:out value="${commodity.commodityprice}"></c:out>
							</p>
						</div>
						<!-- User Profile widget -->
						<div class="widget user">
							<img class="rounded-circle" width="300px" height="auto"
								src="${contextPath}/buyer/owneravatar?commodityownerid=${commodity.commodityownerid}"
								alt="No image">
							<ul class="list-inline mt-20">
								<li class="list-inline-item"><a href=""
									class="btn btn-contact"><c:out value="${owner.username}"></c:out></a></li>
								<li class="list-inline-item"><a href=""
									class="btn btn-contact"><c:out value="${owner.useremail}"></c:out></a></li>
								<li class="list-inline-item"><a href=""
									class="btn btn-offer">See all his commodities</a></li>
							</ul>
						</div>
						<!-- Safety tips widget -->
						<div class="widget disclaimer">
							<h5 class="widget-header">Safety Tips</h5>
							<ul>
								<li>1.Check the number of commodity before you buy</li>
								<li>2.Please communicate with seller using Email</li>
								<li>3.Pay after consideration</li>
							</ul>
						</div>
						<!-- Coupon Widget -->
						<div class="widget disclaimer">
							<!-- 颜色不搭配,更换浅色背景 -->
							<!--	<div class="widget coupon text-center">  -->
							<!-- Coupon description -->
							<h5 class="widget-header">BUY IT!</h5>
							<form action="${contextPath}/buyer/shoppingcart/newcommodity"
								class="row">
								<ul>
									<li><p>Add the commodity to your Shopping Cart</p></li>
									<li><input type="hidden" name="addedcommodityid"
										id="addedcommodityid" class="form-control"
										value="${commodity.commodityid}"> <input type="text"
										name="commoditynumber" id="commoditynumber"
										class="form-control" placeholder="Purchase quantity"></li>
									<li><h5 class="widget-header"></h5></li>
									<li><button type="submit"
											class="btn btn-default btn-success btn-block">Add</button></li>
								</ul>
							</form>
						</div>
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