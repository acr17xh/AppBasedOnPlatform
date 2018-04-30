
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
<title>My peanut bank</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link href="${contextPath}/plugins/jquery-ui/jquery-ui.min.css"
	rel="stylesheet">
<link href="${contextPath}/plugins/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${contextPath}/plugins/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-4 column"></div>
			<div class="col-md-4 column">
				<p class="text-info text-center">
					<em>Welcome ${user.username}, </em>here are your peaunt account and
					transactions for per use.
				</p>
				<form action="${contextPath}/user/turnback" method="post">
					<button type="submit" class="btn btn-default btn-info btn-block">Return
					</button>
				</form>
			</div>
			<div class="col-md-4 column"></div>
		</div>
		<div class="row clearfix">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">

				<ul>
					<li>User name: ${user.username}</li>
					<li>Peaunts: ${account.peanut}</li>
				</ul>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Id</th>
							<th>App</th>
							<th>Date</th>
							<th>Peanuts used</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${plist}" var="ptransaction">
							<tr>
								<td>${ptransaction.ptransactionid}</td>
								<td>${ptransaction.appname}</td>
								<td>${ptransaction.ptransactiondate}</td>
								<td>${ptransaction.peanutsused}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>
</body>
</html>
