<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>Register</title>
<#assign contextPath=request.getContextPath()> <script
	src="${contextPath}/js/jquery-3.2.1.min.js"></script> <script
	src="${contextPath}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
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
							patient.</label><br/>
						<#if registerError??> <#list registerError as
							msg> <label> ${msg!} </label>
						<br />
						</#list> </#if> <#if shiroexception??><label>${shiroexception!}</label></#if>						
					
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
