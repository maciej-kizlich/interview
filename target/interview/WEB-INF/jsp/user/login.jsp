<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <title>Interview</title>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/resources/css/signin.css" />" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>
<body>
<h1>Interview</h1>

<div class="container">
    <h2 class="form-signin-heading">Please sign in</h2>

    <form class="form-signin" role="form" name='loginForm'
          action="<c:url value='/j_spring_security_check' />" method='POST'>
          
          <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <strong>Error!</strong> ${error}
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-info alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                        ${msg}
                </div>
            </c:if>
        <div class="login-inputs">

            <input type="hidden" value="<c:url value="${redirect}"/>" name="redirect"/>

            <div class="input-group input-group-lg">
                <span class="input-group-addon">@</span>
                <input type="email" class="form-control" placeholder="Username" name="username" required autofocus/>
            </div>

            <div class="input-group input-group-lg">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" class="form-control" placeholder="Password" name="password" required/>
            </div>
        </div>
        <div class="bottom-panel">
            <div class="row">
                <div class="col-xs-6 checkbox">
                    <label class="checkbox">
                        <input type="checkbox" value="remember-me">Remember Me
                    </label>
                </div>
                <div class="col-xs-6">
                    <label class="checkbox">
                        <a href="#">Forgot your password?</a>
                    </label>
                </div>
            </div>
        </div>

        <input name="submit" class="btn btn-lg btn-primary btn-block" type="submit" value="Log in"/>
    </form>
    <div class="panel-footer">
        Not Registered? <a href="<c:url value="/user/register" />">Register here</a></div>
</div>

</body>
</html>