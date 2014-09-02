<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="/resources/images/banner-graphic.png" var="banner"/>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <span class="navbar-brand">Office Library</span>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-main">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<spring:url value="/" htmlEscape="true" />"><i class="icon-home icon-black"></i> Home</a></li>
                <li><a href="<spring:url value="/orders/myOrders" htmlEscape="true" />"><i class="icon-book icon-black"></i> My books (remove)</a></li>
                <li><a href="#"><i class="icon-time icon-black"></i> History</a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="<spring:url value="/orders/allOrders" htmlEscape="true" />"><i class="icon-book icon-black"></i> All orders</a></li>
                    <li><a href="<spring:url value="/books/new" htmlEscape="true" />"><i class="icon-plus-sign icon-black"></i> Add new book</a></li>
                    <li><a href="<spring:url value="/user/usersList" htmlEscape="true" />"><i class="icon-user icon-black"></i> Users</a></li>
                </sec:authorize>
                <li><a href="<spring:url value="/user/myProfile" htmlEscape="true" />"><i class="icon-user icon-black"></i> My Profile</a></li>
            </ul>

            <sec:authorize access="isAuthenticated()">
                   <div class="welcome-message">
                    Welcome : ${pageContext.request.userPrincipal.name} | <sec:authorize access="hasRole('ROLE_ADMIN')"><a href="<spring:url value="/admin/showSettings"/>"><span class="glyphicon glyphicon-cog"></span> Settings</a> | </sec:authorize> <a href="<c:url value="/j_spring_security_logout"/>"> Logout</a>
                </div>
            </sec:authorize>

        </div><!--/.nav-collapse -->
    </div>
</div>


