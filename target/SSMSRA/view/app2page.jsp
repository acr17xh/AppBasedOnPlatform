
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
<title>App 2</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${contextPath}/blog/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/css/APP2style.css">

	<script src="${contextPath}/js/jquery-3.2.1.min.js"></script>
	<%--用来导入Google Map API--%>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU&callback=initMap"></script>

<style>
	html,body{
		height:100%;width:100%;padding:0%;margin:0%;
		position: relative;
	}
	#map{
		height:100%;width: 60%;
		position: absolute; left: 0%; top: 8.5%;

	}
	#myDiv{
		width: 39.8%;padding-left: 2%;padding-right: 2%;
		position: absolute; right: 0%; top: 8.5%;
	}
	#button_saver{
		 position: absolute;top: 70%
	}
</style>
</head>


<body>
	<nav class="navbar navbar-default navbar-static-top">
		<h1 class="text-center navbar-text">SHEFFIELD  FOOD  MAP <small>--find your favourite food here</small></h1>

		<form class="navbar-form navbar-left" role="search">
			<p class="navbar-text" style="position: relative;top: 10px">Your location now is marked on Map, please input radius
				<%--<span id="span_lat" class="label label-primary"></span>--%>
				<%--<span id="span_lng" class="label label-primary"></span>--%>
			</p>
			<div class="form-group" style="position: relative">
				<input style="position: relative;top: 10px;width: 400px"
					   id="NumRad" type="text" class="form-control" placeholder="Searching radius (from 10 ~ 50000)">
			</div>
			<button style="position: relative;top: 10px"
					id="getRest" type="button" class="btn btn-success">Get Restaurant</button>
		</form>
	</nav>

	<div id="map" ></div>
	<div id="myDiv"></div>


	<script>
        //部分google map功能如标记在jsp script标签中实现

        //initMap()方法用来初始化并添加地图；
        //Map构造函数格式为：Map(mapDiv:Node , options:MapOptions )

        var map;
        var lat1;
        var lng1;
        var radius;
        //        var lng_display;
        //        var lat_display;

        function initMap() {
            map = new google.maps.Map(document.getElementById("map"), {
                zoom: 15,
				styles:[
                    {
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#ebe3cd"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#523735"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.stroke",
                        "stylers": [
                            {
                                "color": "#f5f1e6"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#c9b2a6"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#dcd2be"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#ae9e90"
                            }
                        ]
                    },
                    {
                        "featureType": "landscape.natural",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#93817c"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.business",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "geometry.fill",
                        "stylers": [
                            {
                                "color": "#a5b076"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#447530"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#f5f1e6"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.icon",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "road.arterial",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#fdfcf8"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#f8c967"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#e9bc62"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway.controlled_access",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#e98d58"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway.controlled_access",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#db8555"
                            }
                        ]
                    },
                    {
                        "featureType": "road.local",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#806b63"
                            }
                        ]
                    },
                    {
                        "featureType": "transit",
                        "stylers": [
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#8f7d77"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "labels.text.stroke",
                        "stylers": [
                            {
                                "color": "#ebe3cd"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.station",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "water",
                        "elementType": "geometry.fill",
                        "stylers": [
                            {
                                "color": "#b9d3c2"
                            }
                        ]
                    },
                    {
                        "featureType": "water",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#92998d"
                            }
                        ]
                    }
                ]
            });

            //获取当前设备所在地区位置
            var infoWindow = new google.maps.InfoWindow({map: map});
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    var pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                    infoWindow.setPosition(pos);
                    infoWindow.setContent('You are here man');
                    map.setCenter(pos);

                    //LatLng Object
                    var initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    //give lat and lng to lag_search and lng_search
					lat1 = initialLocation.lat().toString();
                    lng1 = initialLocation.lng().toString();
//                    lat_display = initialLocation.lat().toFixed(2).toString();
//                    lng_display = initialLocation.lng().toFixed(2).toString();

                }, function() {
                    handleLocationError(true, infoWindow, map.getCenter());
                });
            } else {
                // Browser doesn't support Geolocation
                handleLocationError(false, infoWindow, map.getCenter());
            }
            //定位异常处理程序
            function handleLocationError(browserHasGeolocation, infoWindow, pos) {
                infoWindow.setPosition(pos);
                infoWindow.setContent(browserHasGeolocation ?
                    'Error: The Geolocation service failed.' :
                    'Error: Your browser doesn\'t support geolocation.');
            }
        }

        //JQuery begin to manipulate AJAX
        $(document).ready(function () {

            var Allmarker = new Array(); //This arry used to save marker标记
            //This function defined to clean last jumping marker
            function CleanAnimation() {
                for (var q = 0; q < Allmarker.length;){
                    Allmarker[q].setAnimation(null);
                    q++;
                }
            }
            //This function defined to add new marker, and then push it in the array;-1.4790000
            //The content in the push() is the process that create new marker.
            function addMarker(position, timeout) {
                window.setTimeout(function() {
                    Allmarker.push(new google.maps.Marker({
                        position: position,
                        map: map,
                        animation: google.maps.Animation.DROP
                    }));
                }, timeout);
            }

//            document.getElementById("span_lat").innerHTML = lat_display;
//            document.getElementById("span_lng").innerHTML = lng_display;


            $("#getRest").click(function () {
                //Clear old data
                document.getElementById("myDiv").innerHTML = "";

                //Get the value of input when click button
                radius = $("#NumRad").val();

                if (radius < 10){
                    alert("Searching failed:" + "\n" +
						"The radius is shorter than 10, please input from 10 to 50000");
                    $("#myDiv").append("<img style='width: 700px;height: 400px' class='center-block' " +
						"src='https://tincan.co.uk/sites/default/files/styles/banner_image_tincan/public/images/Blog-404-01.png?itok=G8jyKL7k'/>");
					$("#myDiv").append("<h2 class='text-center'>Oops! There are something wrong that we can't find the result.</h2>");

                }
				else if(radius > 50000){
                    alert("Searching failed:" + "\n" +
						" The radius is bigger than 50000, please input from 10 to 50000")
                    $("#myDiv").append("<img style='width: 700px;height: 400px' class='center-block' " +
                        "src='https://tincan.co.uk/sites/default/files/styles/banner_image_tincan/public/images/Blog-404-01.png?itok=G8jyKL7k'/>");
                    $("#myDiv").append("<h2 class='text-center'>Oops! There are something wrong that we can't find the result.</h2>");

                }
				else {

                //Create the "Searching ended" <P> to mark the position, helping insert the json data
                var ending = document.createElement("p");
                var ending_node = document.createTextNode("Searching ended");
                var mydiv = document.getElementById("myDiv");
                mydiv.appendChild(ending); ending.appendChild(ending_node);
                $("#myDiv > p").attr({"id":"ending"}) //add a id for next process,insert json data
                //Creat title of this div
                $("#myDiv > p").before("<h1 class='text-center'>Restaurants Information</h1>");

                $.ajax({
                    url:"${contextPath}/home/detail",
                    contentType: 'application/x-www-form-urlencoded',
                    type:'POST',
					data:{
                        lat:lat1,
						lng:lng1,
						radius:radius
					},
                    dataType:"json",
                    success:function (data) {
                        var Number = 0;

                        //data is returned by @responseBody, it's a javascript object(json object)
                        //遍历整个json获取嵌套数据
                        for(restaurant in data) {

                            //Count for Number
                            Number = Number + 1;

                            var disArea = document.createElement("div");
                            disArea.style.position = "relative";
                            disArea.style.paddingTop = "5%";
                            disArea.style.paddingBottom = "5%";
                            disArea.style.height = "16%";
                            disArea.setAttribute("class","hdn");

                            var name = document.createElement("h3");
                            var nameEntity = document.createTextNode(Number+"."+data[restaurant].name+":");
							name.setAttribute("class","text-left");
                            name.setAttribute("class","lead");
                            name.setAttribute("style","marginTop:0%");
                            name.appendChild(nameEntity);
                            disArea.appendChild(name);

                            var address = document.createElement("p");
                            var addressWord = document.createTextNode("Address:  ");
                            var addressEntity = document.createTextNode(data[restaurant].vicinity);
                            address.setAttribute("class","text-success");
                            address.appendChild(addressWord);
                            address.appendChild(addressEntity);
                            disArea.appendChild(address);

                            var rating = document.createElement("p");
                            var ratingWord = document.createTextNode("Rating: ");
                            var ratingEntity = document.createTextNode(data[restaurant].rating);
                            rating.setAttribute("class","text-success");
                            rating.appendChild(ratingWord);
                            rating.appendChild(ratingEntity);
                            disArea.appendChild(rating);

                            //Display the necessary image model
                            var PreImgPath = data[restaurant].restaurant_photo.photo_reference;
                            var Maxheight = data[restaurant].restaurant_photo.height;
                            var Maxwidth = data[restaurant].restaurant_photo.width;
                            var GoogleImgPath = "https://maps.googleapis.com/maps/api/place/photo?"+
                                "maxwidth="+ Maxwidth+
                                "&maxheight"+ Maxheight+
                                "&photoreference="+PreImgPath+
                                "&key="+"AIzaSyBI87YwMOnQw5W3HgQ7A-x60FBNXpR8lKU";
                            //Create a new image to contain picture
                            function pic() {
                                var im = new Image();
                                im.src = GoogleImgPath;
                                //style.width则需要添加双引号和px
                                im.style.width = "200px";
                                im.style.height = "200px";
                                im.style.position = "absolute";
                                im.style.right = "0%";
                                im.style.top = "20%";
                                im.setAttribute("class","img-circle");
                                disArea.appendChild(im);
                            }
                            pic();

                            //Click button to return detail information model
                            var butt = document.createElement("button");
                            butt.style.height="40px";
                            butt.id = Number;
                            butt.type = "submit";
                            butt.innerHTML = "Find it on Map";
                            butt.value = data[restaurant].name;
                            butt.name = "restname";
                            butt.setAttribute("class","btn-success btn");
                            butt.addEventListener("click",function () {

                                //Make mark jump on the map;
                                //Notice: use this.id and this.value! to get current button's attribute! 注意使用this.代替butt.
                                var n = this.id - 1;
                                CleanAnimation();
                                Allmarker[n].setAnimation(google.maps.Animation.BOUNCE);

                            },false);
                            disArea.appendChild(butt);

                            //Mark the restaurant's position in the map************标记餐厅位置**************
                            var rst_lat = data[restaurant].lat;
                            var rst_lng = data[restaurant].lng;
                            var rst_position = {lat: rst_lat, lng: rst_lng};
                            var rst_timeout = Number * 60;

                            addMarker(rst_position,rst_timeout);

                            $("#ending").before(disArea);

                        }
                        $('.hdn').css('visibility','visible').hide().fadeIn(1500);
                        $("#myDiv > p").css('visibility','visible').hide().fadeIn(1500);
                        $("#myDiv > h3").css('visibility','visible').hide().fadeIn(1500);
                    },
                    error:function () {
                        alert("read error");
                    }

                });

				}
            });

        });

		//keep the map fixed
        $(function(){
            $(window).scroll(function(){
                var yy = $(this).scrollTop();//获得滚动条top值
                if ($(this).scrollTop() < 50) {
                    $("#map").css({"position":"absolute",top:"50px",left:"0%"}); //设置div层定位，要绝对定位
                }else{
                    $("#map").css({"position":"absolute",top:yy+"px",left:"0%"});
                }
            });

        });
	</script>

<%--<form id="abc" onsubmit="return false">--%>
	<%--<input type="hidden" name="restname" value="Hui Wei">--%>
	<%--<button type="submit" name="abcbutton">hahahahahahah</button>--%>
<%--</form>--%>



</body>
</html>
