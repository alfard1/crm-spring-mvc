<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html lang="en">

<head>
	<title>Products | Spring CRM</title>
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
        			<a class="nav-link active" href="${pageContext.request.contextPath}/product/list">Products<span class="sr-only">(current)</span></a>
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
	
	<div class="content">
		<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
			<div class="add-object">			
				<input type="button" value="Add Product" onclick="window.location.href='showFormForAdd'; return false;" class="btn btn-primary"/>
			</div>
		</security:authorize>
	
		<div class="main-table">
			<table class="table table-striped table-responsive">
				<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Name</th>
										
					<%-- Only show "Action" column for managers or admin --%>
					<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
						<th scope="col">Action</th>
					</security:authorize>
				</tr>
				</thead>
				
				<tbody>
				<!-- loop over and print our customers -->
				<c:forEach var="tempProduct" items="${products}">
				
					<!-- construct an "update" link with product id -->
					<c:url var="updateLink" value="/product/showFormForUpdate">
						<c:param name="productId" value="${tempProduct.id}" />
					</c:url>					

					<!-- construct an "delete" link with customer id -->
					<c:url var="deleteLink" value="/product/delete">
						<c:param name="productId" value="${tempProduct.id}" />
					</c:url>					
					
					<tr>
						<td> ${tempProduct.id} </td>
						<td> ${tempProduct.name} </td>
						
						<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
							<td>
								<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
									<!-- display the update link -->
									<a href="${updateLink}" class="badge badge-secondary">Update</a>
								</security:authorize>
	
								<security:authorize access="hasAnyRole('ADMIN')">
									<a href="${deleteLink}" class="badge badge-danger" onclick="return confirmDelete(this);">Delete</a>
								</security:authorize>
							</td>
						</security:authorize>
					</tr>
				</c:forEach>
				</tbody>		
			</table>
		</div>
	</div>
	</div>
	</div>
	
	<div class="legend-with-padding">
		<div class="legend-background">
			<div class="legend">			
				<p>Permissions for managing products:</p>
				<ul>
					  <li>employee - view</li>
					  <li>manager - as employee + edit </li>
					  <li>admin - as manager + delete</li>
				</ul>
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
    <script src="${pageContext.request.contextPath}/resources/js/bootbox.min.js"></script>
    
    <!--  script for bootbox.js confirmation used to confirm before deleting objects  -->
    <script>
    
    function confirmDelete(sender) {
        if ($(sender).attr("confirmed") == "true") {return true;}

        bootbox.confirm("Are you sure you want to delete?", function (confirmed) {
            if (confirmed) {
                $(sender).attr('confirmed', confirmed);
                sender.click();
            }
        });

    return false;
    }
    
    </script>
</body>
</html>










