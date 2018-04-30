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
<script src="${contextPath}/js/fileinput.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-*.min.js"></script>
<style type="text/css">
body {
	background: url("${contextPath}/images/wallpaper20171209.jpg") no-repeat;
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
	
</script>
</head>
<body>
	<div class="container">
		<div class="row row-centered">
			<div class="well col-md-6 col-centered">
				<h2>Your new commodity</h2>
				<form action="${contextPath}/user/useraddress/newaddress"
					method="post" role="form">
					<div class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-user" aria-hidden="true"></i></span> <label>Address
							Description</label>
						<textarea class="form-control" id="addressdescription"
							name="addressdescription" rows="20"></textarea>
					</div>
					<button type="submit" class="btn btn-success btn-block">Submit</button>
				</form>
				<br />
				<form action="${contextPath}/user/userdashboard" method="post"
					role="form">
					<button type="submit" class="btn btn-success btn-block">Return</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
