<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>

    <!-- SITE TITTLE -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sheffield Secondhank Book Flea Market</title>

    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

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

    <script type="text/javascript">
        function selectChangeValue1() {
            document.getElementById('selectform1').submit();
        }

        function selectChangeValue2() {
            document.getElementById('selectform2').submit();
        }
    </script>
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
                                </div>
                            </li>
                        </ul>
                        <shiro:guest>
                            <ul class="navbar-nav ml-auto mt-10">
                                <li class="nav-item"><a class="nav-link login-button"
                                                        href="${contextPath}/home/loginpage">Login</a></li>
                            </ul>
                        </shiro:guest>
                        <shiro:user>
                            <ul class="navbar-nav ml-auto mt-10">
                                <!-- <li class="nav-item"><a class="nav-link add-button"
										href="${contextPath}/redpacket/redpacketpage"><i
											class="fa fa-plus-circle"></i>Grab Red Packet</a></li> -->

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
                                <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0"
                                       id="search" name="searchcontent"
                                       placeholder="Search for store">
                            </div>
                            <div class="form-group col-md-2">
                                <button type="submit" class="btn btn-primary">Search
                                    Now
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="section-sm">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="search-result bg-gray" id="numberofresults">
                    <h2>Results for "${searchtext}"</h2>
                    <p>"${numberofresults}" Results on "${datewhensearch}"</p>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="category-sidebar">
                    <div class="widget category-list">
                        <h4 class="widget-header">Price Range</h4>
                        <div class="block">
                            <form action="${contextPath}/search/searchbypricerange">
                                <ul class="category-list">
                                    <li>From:</li>
                                    <li><input id="fromprice" type="text" class="span2"
                                               value="" name="fromprice" data-slider-min="10"
                                               data-slider-max="1000" data-slider-step="5"
                                               data-slider-value="[250,450]"/></li>
                                    </br>
                                    <li>To:</li>
                                    <li><input id="toprice" type="text" class="span2"
                                               value="" name="toprice" data-slider-min="10"
                                               data-slider-max="1000" data-slider-step="5"
                                               data-slider-value="[250,450]"/></li>
                                    </br>
                                    <li>
                                        <button type="submit" class="btn btn-primary">Search</button>
                                    </li>
                                </ul>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="category-search-filter">
                    <div class="row">
                        <div class="col-md-6">
                            <strong>Short</strong>
                            <form
                                    action="${contextPath}/search/conditions/0?time=${currenttimeinurl}#numberofresults"
                                    method="post" id="selectform1">
                                <select onchange="this.form.submit()" name="selectedcondition">
                                    <c:choose>
                                        <c:when test="${selectedcondition==null}">
                                            <option value="1">Most Recent</option>
                                            <option value="2" selected="selected">Most Popular</option>
                                            <option value="3">Lowest Price</option>
                                            <option value="4">Highest Price</option>
                                        </c:when>
                                        <c:when test="${selectedcondition==1}">
                                            <option value="1" selected="selected">Most Recent</option>
                                            <option value="2">Most Popular</option>
                                            <option value="3">Lowest Price</option>
                                            <option value="4">Highest Price</option>
                                        </c:when>
                                        <c:when test="${selectedcondition==2}">
                                            <option value="1">Most Recent</option>
                                            <option value="2" selected="selected">Most Popular</option>
                                            <option value="3">Lowest Price</option>
                                            <option value="4">Highest Price</option>
                                        </c:when>
                                        <c:when test="${selectedcondition==3}">
                                            <option value="1">Most Recent</option>
                                            <option value="2">Most Popular</option>
                                            <option value="3" selected="selected">Lowest Price</option>
                                            <option value="4">Highest Price</option>
                                        </c:when>
                                        <c:when test="${selectedcondition==4}">
                                            <option value="1">Most Recent</option>
                                            <option value="2">Most Popular</option>
                                            <option value="3">Lowest Price</option>
                                            <option value="4" selected="selected">Highest Price</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="1">Most Recent</option>
                                            <option value="2">Most Popular</option>
                                            <option value="3">Lowest Price</option>
                                            <option value="4">Highest Price</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <div class="view">
                                <strong>Views</strong>
                                <ul class="list-inline view-switcher">
                                    <li class="list-inline-item"><a
                                            href="javascript:void(0);"><i class="fa fa-th-large"></i></a>
                                    </li>
                                    <li class="list-inline-item"><a
                                            href="javascript:void(0);"><i class="fa fa-reorder"></i></a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="product-grid-list">
                    <div class="row mt-30">
                        <c:forEach items="${commoditylist}" var="commodity">
                            <div class="col-sm-12 col-lg-4 col-md-6">
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
                                                        class="fa fa-folder-open-o"></i>${commodity.commoditycategory}
                                                </a></li>
                                                <li class="list-inline-item"><a
                                                        href="${contextPath}/buyer/commodity?commodityid=${commodity.commodityid}"><i
                                                        class="fa fa-calendar"></i>${commodity.commoditydate}</a></li>
                                            </ul>
                                            <ul>
                                                <li><p class="card-text">${commodity.commoditydescription}</p></li>
                                                <li><p class="card-text">Price: ${commodity.commodityprice}</p></li>
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
                <div class="pagination justify-content-center">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/search/conditions/0?time=${currenttimeinurl}#numberofresults"
                                                     aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">First</span>
                            </a></li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/search/conditions/1?time=${currenttimeinurl}#numberofresults">Prev</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/search/conditions/2?time=${currenttimeinurl}#numberofresults">Next</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/search/conditions/3?time=${currenttimeinurl}#numberofresults"
                                                     aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Last</span>
                            </a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
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