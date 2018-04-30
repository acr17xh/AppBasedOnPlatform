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

</head>
<body>
	<div class="container">
		<div class="row row-centered">
			<div class="well col-md-6 col-centered">
				<h2>Welcome</h2>
				<form action="${contextPath}/home/register/submission" method="post"
					role="form">
					<div class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-user" aria-hidden="true"></i></span> <input
							type="text" class="form-control" id="username" name="username"
							placeholder="Username" />
					</div>
					<div class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-certificate"></i></span> <input
							type="password" class="form-control" id="password"
							name="userpassword" placeholder="Password" />
					</div>
					<div class="input-group input-group-md">
						<span class="input-group-addon" id="sizing-addon1"><i
							class="glyphicon glyphicon-envelope"></i></span> <input type="email"
							class="form-control" id="email" name="useremail"
							placeholder="Email" />
					</div>

					<br />
					<button type="submit" class="btn btn-success btn-block">Submit</button>
					<div class="input-group input-group-md">
						<label>Encrypt password may take some time, please be
							patient.</label><br />
						<c:if test="${registerError.size()!=0}">
							<c:forEach items="${registerError}" var="msg">
								<label> ${msg} </label>
							</c:forEach>
						</c:if>
						<br />
						<c:if test="${shiroexception!=null}">
							<label>${shiroexception}</label>
						</c:if>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
