<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<title>More info about the project | Spring CRM</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
	<!-- Reference Bootstrap files -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

	<!-- reference our style sheet -->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />

</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/home">Spring CRM</a>
  		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
  		</button>

  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
    		<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/customer/list">Customers</a>
				</li>
				<li class="nav-item">
        			<a class="nav-link" href="${pageContext.request.contextPath}/product/list">Products</a>
      			</li>
      			<li class="nav-item">
        			<a class="nav-link" href="${pageContext.request.contextPath}/comments/list">Comments</a>
      			</li>
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/api">API</a>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="${pageContext.request.contextPath}/more-info">About project<span class="sr-only">(current)</span></a>
				</li>
			</ul>
    		  
    
	        <span class="navbar-text">			
				<b><security:authentication property="principal.username" />&nbsp</b>
				<security:authentication property="principal.authorities" />
			</span>
	        
			<form:form>
				<button type="submit" formaction="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">Logout</button> 
			</form:form> 
  		</div>
	</nav>
	<div class="container">
	<div class="mx-auto">
		<div style="margin-top: 50px;">
			<h3>TECHNOLOGIES USED</h3>
			<br>

			<br>
			<div class="card bg-secondary text-white">
				<div class="card-body">Front-end</div>
			</div>
			<br>



			<p>For front-end I used <b>Bootstrap</b> (Bootstrap 3 is used for login/registration pages and
				Bootstrap 4 for the rest. I added also <a href="http://bootboxjs.com/"><b>BootboxJS</b></a>
				(script is used for conformation popup before delete objects)</p>

			<br>
			<div class="card bg-secondary text-white">
				<div class="card-body">Back-end </div>
			</div>
			<br>

			<p>Backend is built on <b>Spring MVC Framework</b>. I used Spring all Java configuration (no xml)
				and Maven for project dependency management. I used following versions:</p>

			<ul>
				<li>Spring 5.1.6</li>
				<li>Hibernate 5.4.1</li>
				<li>Spring Security 5.1.5</li>
				<li>Maven 1.8</li>
			</ul>


			<br>
			<div class="card bg-secondary text-white">
				<div class="card-body">Development</div>
			</div>
			<br>
			
			<p>App is hosted on <b>Amazon Web Services</b> using: EC2, RDS (MySQL) and Elastic Beanstalk. For VCS I
				used <b>Git</b>. During development process I was working on <b>IntelliJ IDEA</b>,
				but before I worked with Eclipse, NetBeans and Visual Studio Code.</p>


			<br>
			<div class="card bg-secondary text-white">
				<div class="card-body">API</div>
			</div>
			<br>

			<p>Restricted REST APIs based on roles, built as separate API Service. Used Spring all Java configuration
				(no xml) and Maven for project dependency management. Full documentation for API is available here:
				<a href=${pageContext.request.contextPath}/api>API documentation</a>.</p>

			<br><br><br>
		</div>
		
	</div>
	</div>
		
		<div class="footer">
		<footer class="py-2 bg-dark">
  			<div class="container text-center text-light">
  				<small>&#169; 2019 Copyright: <a href=mailto:pawel.wysokinski@gmail.com> Pawel Wysokinski</a></small>
  			</div>
		</footer>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
