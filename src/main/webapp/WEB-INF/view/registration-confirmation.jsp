<!doctype html>
<html lang="en">

<head>
	<title>Registration Confirmation | Spring CRM</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- Reference Bootstrap files -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

		<div id="loginbox" style="margin-top: 50px; min-width: 300px;" class="col-md-3 col-md-offset-4 col-sm-6 col-sm-offset-3">
			
			<div class="panel panel-primary">

				<div class="panel-heading">
					<div class="panel-title">Confirmation</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">
					<div class="alert alert-success ">User registered successfully</div>
					<a href="${pageContext.request.contextPath}/showMyLoginPage" class="btn btn-success btn-block pull-right">Login with new user</a>
				</div>

			</div>
					
		</div>	
</body>
</html>