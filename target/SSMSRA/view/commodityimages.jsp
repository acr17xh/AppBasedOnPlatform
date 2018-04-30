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
<title>My Home Page</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="${contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${contextPath}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/fileinput.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/font-awesome.min.css">
<script
	src="${contextPath}/js/piexifjs-master/piexifjs-master/piexif.js"></script>
<script src="${contextPath}/js/fileinput.min.js"></script>
<style type="text/css">
body {
	background: url("${contextPath}/images/mont_saint_michel_france_island_bay_119491_4734x3136.jpg") no-repeat;
	background-size: 100% auto;
}

.container {
	display: table;
	height: 100%;
}

.row {
	display: table-cell;
	vertical-align: middle;
}
/* centered columns styles */
.row-centered {
	text-align: center;
}

.col-centered {
	display: inline-block;
	float: none;
	text-align: left;
	margin-right: -4px;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#fileinput1").fileinput({
			uploadUrl : '${contextPath}/user/commodityimages',
			uploadAsync : true,
			showPreview : true,
			maxImageWidth : 1920,
			maxImageHeight : 1080,
			resizeImage : true,
			maxFilePreviewSize : 16384,
			maxFileSize : 16384,
			maxFileCount : 5,
			allowedFileExtensions : [ "jpg" ]
		});
	});

	//防止页面后退
	history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function() {
		history.pushState(null, null, document.URL);
	});
</script>

</head>
<body>
	<div class="container">
		<div class="row row-centered">
			<div class="well col-md-12 col-centered">
				<h2>Images</h2>
				<div class="input-group input-group-md">
					<span class="input-group-addon" id="sizing-addon1"><i
						class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
					<div class="file-loading">
						<input id="fileinput1" name="fileinput1[]" type="file" multiple>
					</div>
				</div>
				<br />
				<div class="input-group input-group-md">
					<label>Please upload 1~5 images, limited in 16MB each,
						.JPEG format.</label>
				</div>
				<br />
				<form action="${contextPath}/user/userdashboard" method="post"
					role="form">
					<button type="submit" class="btn btn-success btn-block">Finish</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
