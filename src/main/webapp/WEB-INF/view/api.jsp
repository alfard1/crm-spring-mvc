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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

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
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/api">API<span class="sr-only">(current)</span></a>
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
<div class="container">
    <div class="mx-auto">

        <div style="margin-top: 50px;">


            <div class="card bg-secondary text-white">
                <div class="card-body">Introduction:</div>
            </div>

            <br>

            <p>This is secured REST APIs, access is restricted based on roles. I used Spring all Java configuration
                (no xml) and Maven for project dependency management. API supports all CRUD operations for sample
                entity (Customer). </p>

            <br><br>

            <div class="card bg-secondary text-white">
                <div class="card-body">Allowed HTTPs requests:</div>
            </div>

            <br>

            <div class="main-table">
                <table class="table table-striped table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">HTTP Method</th>
                        <th scope="col">CRUD Operation</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>POST</td>
                        <td>Create a new customer</td>
                    </tr>
                    <tr>
                        <td>GET</td>
                        <td>Read a list of all customers or single entity</td>
                    </tr>
                    <tr>
                        <td>PUT</td>
                        <td>Update an existing customer</td>
                    </tr>
                    <tr>
                        <td>DELETE</td>
                        <td>Delete an existing customer</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <br><br>

            <div class="card bg-secondary text-white">
                <div class="card-body">Object attributes:</div>
            </div>

            <br>

            <div class="main-table">
                <table class="table table-striped table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Type</th>
                        <th scope="col">Description</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>id</td>
                        <td>int</td>
                        <td>Automatically generated value by CRM Service.</td>
                    </tr>
                    <tr>
                        <td>firstName</td>
                        <td>String</td>
                        <td>First name of customer. Can not be empty. Requires minimum 1 character.</td>
                    </tr>
                    <tr>
                        <td>lastName</td>
                        <td>String</td>
                        <td>Last name of customer. Can not be empty. Requires minimum 1 character.</td>
                    </tr>
                    <tr>
                        <td>email</td>
                        <td>String</td>
                        <td>Email address for customer</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <br><br>

            <div class="card bg-secondary text-white">
                <div class="card-body">Sample use-cases:</div>
            </div>

            <br>

            <div id="accordion">
                <div class="card">
                    <div class="card-header">
                        <a class="card-link" data-toggle="collapse" href="#collapse1">
                            Case 1: Select all customers
                        </a>
                    </div>
                    <div id="collapse1" class="collapse show" data-parent="#accordion">
                        <div class="card-body">
                            <div class="main-table">
                                <table class="table table-striped table-responsive">

                                    <tbody>
                                    <tr>
                                        <td>HTTP method:</td>
                                        <td>GET</td>
                                    </tr>
                                    <tr>
                                        <td>Link:</td>
                                        <td>springcrm-api.neg.edu.pl/api/customers/list</td>
                                    </tr>
                                    <tr>
                                        <td>Body:</td>
                                        <td>[empty]</td>
                                    </tr>
                                    <tr>
                                        <td>Authorization:</td>
                                        <td style="color: red">Required</td>
                                    </tr>
                                    <tr>
                                        <td>Access restricted for:</td>
                                        <td>Employees, Managers, Admins</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapse2">
                            Case 2: Select a single customer with id = 6
                        </a>
                    </div>
                    <div id="collapse2" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <div class="main-table">
                                <table class="table table-striped table-responsive">

                                    <tbody>
                                    <tr>
                                        <td>HTTP method:</td>
                                        <td>GET</td>
                                    </tr>
                                    <tr>
                                        <td>Link:</td>
                                        <td>springcrm-api.neg.edu.pl/api/customers/list/6</td>
                                    </tr>
                                    <tr>
                                        <td>Body:</td>
                                        <td>[empty]</td>
                                    </tr>
                                    <tr>
                                        <td>Authorization:</td>
                                        <td style="color: red">Required</td>
                                    </tr>
                                    <tr>
                                        <td>Access restricted for:</td>
                                        <td>Employees, Managers, Admins</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapse3">
                            Case 3: Creating a new customer
                        </a>
                    </div>
                    <div id="collapse3" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <div class="main-table">
                                <table class="table table-striped table-responsive">

                                    <tbody>
                                    <tr>
                                        <td>HTTP method:</td>
                                        <td>POST</td>
                                    </tr>
                                    <tr>
                                        <td>Link:</td>
                                        <td>springcrm-api.neg.edu.pl/api/customers/new</td>
                                    </tr>
                                    <tr>
                                        <td>Body:</td>
                                        <td>
                                            {
                                            "firstName": "Jan",
                                            "lastName": "Nowak",
                                            "email": "j.nowak@gmail.com"
                                            }
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Authorization:</td>
                                        <td style="color: red">Required</td>
                                    </tr>
                                    <tr>
                                        <td>Access restricted for:</td>
                                        <td><del>Employees</del>, Managers, Admins</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapse4">
                            Case 4: updating an existing customer with id = 52
                        </a>
                    </div>
                    <div id="collapse4" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <div class="main-table">
                                <table class="table table-striped table-responsive">

                                    <tbody>
                                    <tr>
                                        <td>HTTP method:</td>
                                        <td>PUT</td>
                                    </tr>
                                    <tr>
                                        <td>Link:</td>
                                        <td>springcrm-api.neg.edu.pl/api/customers/update</td>
                                    </tr>
                                    <tr>
                                        <td>Body:</td>
                                        <td>
                                            {
                                            "id": 52,
                                            "firstName": "Jan",
                                            "lastName": "Nowak",
                                            "email": "jan@gmail.com"
                                            }
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Authorization:</td>
                                        <td style="color: red">Required</td>
                                    </tr>
                                    <tr>
                                        <td>Access restricted for:</td>
                                        <td><del>Employees</del>, Managers, Admins</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#collapse5">
                            Case 5: deleting an existing customer with id = 52
                        </a>
                    </div>
                    <div id="collapse5" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <div class="main-table">
                                <table class="table table-striped table-responsive">

                                    <tbody>
                                    <tr>
                                        <td>HTTP method:</td>
                                        <td>DELETE</td>
                                    </tr>
                                    <tr>
                                        <td>Link:</td>
                                        <td>springcrm-api.neg.edu.pl/api/customers/delete/52</td>
                                    </tr>
                                    <tr>
                                        <td>Body:</td>
                                        <td>[empty]</td>
                                    </tr>
                                    <tr>
                                        <td>Authorization:</td>
                                        <td style="color: red">Required</td>
                                    </tr>
                                    <tr>
                                        <td>Access restricted for:</td>
                                        <td><del>Employees, Managers</del>, Admins</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <br><br>

            <div class="card bg-secondary text-white">
                <div class="card-body">Sample accesses:</div>
            </div>

            <br>

            <div class="main-table">
                <table class="table table-striped table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">Login</th>
                        <th scope="col">Password</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>employee</td>
                        <td>fun123</td>
                    </tr>
                    <tr>
                        <td>manager</td>
                        <td>fun123</td>
                    </tr>
                    <tr>
                        <td>admin</td>
                        <td>fun123</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <br>

            <br>
            <br>


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


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>





