<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!doctype html>
<html lang="en">

<head>
	
	<title>Login Page | Spring CRM</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- Reference Bootstrap files -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</head>

<body>

	<div>
		
		<div id="loginbox" style="margin-top: 50px;"
			class="col-md-3 col-md-offset-4 col-sm-6 col-sm-offset-3">
			
			<div class="panel panel-primary">

				<div class="panel-heading">
					<div class="panel-title">Sign In</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">

					<!-- Login Form -->
					<form action="${pageContext.request.contextPath}/authenticateTheUser" 
						  method="POST" class="form-horizontal">

					    <!-- Place for messages: error, alert etc ... -->
					    <div class="form-group">
					        <div class="col-xs-15">
					            <div>
								
									<!-- Check for login error -->
								
									<c:if test="${param.error != null}">
										
										<div class="alert alert-danger col-xs-offset-1 col-xs-10">
											Invalid username and password.
										</div>
		
									</c:if>
										
									<!-- Check for logout -->

									<c:if test="${param.logout != null}">
										            
										<div class="alert alert-success col-xs-offset-1 col-xs-10">
											You have been logged out.
										</div>
								    
									</c:if>
									
					            </div>
					        </div>
					    </div>

						<!-- User name -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 							
							<input type="text" name="username" placeholder="username" class="form-control">
						</div>

						<!-- Password -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
							<input type="password" name="password" placeholder="password" class="form-control" >
						</div>

						<!-- Login, Register Buttons -->
						<div style="margin-top: 10px" class="form-group">						
							<div class="container">
								<a href="${pageContext.request.contextPath}/register/showRegistrationForm" class="btn btn-default" role="button" aria-pressed="true">Register New User</a>
								<button type="submit" class="btn btn-primary">Login</button>
							</div>
						</div>


						<!-- legend users / passwords -->

						<div style="margin-top: 0px; margin-left: 0px; font-size: 12px" class="form-group">
						
						<hr style="border-top: 1px dotted #337ab7; margin: 0px 0px 14px -16px;">
						
						<p>Sample accounts:</p>
						<ul>
						  <li>employee / fun123</li>
						  <li>manager / fun123</li>
						  <li>admin / fun123</li>
						</ul>
						
						</div>

						<!-- manually adding tokens -->

						<input type="hidden"
							   name="${_csrf.parameterName}"
							   value="${_csrf.token}" />
						
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
