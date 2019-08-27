<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<title>Save Customer | Spring CRM</title>
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
				<li class="nav-item active">
					<a class="nav-link" href="${pageContext.request.contextPath}/customer/list">Customers<span class="sr-only">(current)</span></a>
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
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/more-info">About project</a>
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
	
	<div class="row justify-content-lg-center" style="margin-right: 0px;">
	<div id="container" style="margin-top: 50px; margin-left: 20px; margin-right: 20px;" class="col-lg-auto">
		<h3>Save Customer</h3>
	
		<div>
			<p>Fill out the form. Asterisk (*) means required.</p>
		</div>

	
		<form:form action="saveCustomer" modelAttribute="customer">

			<!-- need to associate this data with customer id -->
			<form:hidden path="id" />
			
			
			<div class="form-group">
				<label>First name (*):</label>
				<form:input path="firstName" class="form-control" rows="3" />
				<form:errors path="firstName" cssClass="error" />
			</div>

			<div class="form-group">
				<label>Last name (*):</label>
				<form:input path="lastName" class="form-control" rows="3" />
				<form:errors path="lastName" cssClass="error" />
			</div>			
			
			<div class="form-group">
   				 <label>Email address (*)</label>
   				 <form:input path="email" class="form-control" placeholder="name@example.com"/>
 				 <form:errors path="email" cssClass="error" />
  			</div>

		<a href="${pageContext.request.contextPath}/customer/list"
				class="btn btn-light">Back to List</a>
			<input type="submit" value="save" class="btn btn-primary" />

		</form:form>

	</div>
	</div>
	
	<div class="footer">
		<footer class="py-2 bg-dark">
  			<div class="container text-center text-light">
  				<small>&#169; 2019 Copyright: <a href=mailto:pawel.wysokinski@gmail.com> Pawel Wysokinski</a></small>
  			</div>
		</footer>
	</div>

	<!-- Reference Bootstrap scripts -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>
