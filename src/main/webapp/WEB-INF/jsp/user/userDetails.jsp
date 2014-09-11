<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

<br><br>
<div class="container-fluid well span6">
	<div class="row-fluid">
        <div class="span2" >
		    <img src="http://localhost:8080/interview/resources/image/anon_user.png" class="img-circle">
        </div>
        
        <div class="span8">
            <h3>${user.username}</h3>
            <h6>Email: ${user.username}</h6>
            <h6>Data ostatniego logowania: ${user.lastLoginDate}</h6>
            <h6>  Data rejestracji: ${user.userCreationDate} </h6>
        </div>
        <div class="span2">
<div style="float: right">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <spring:url value="/user/edit/{userId}" var="userEditUrl" htmlEscape="true">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <form action="${userEditUrl}" >
                        <input type="hidden" value="${user.id}" />
                        <input class="btn btn-warning" type="submit" value="Edytuj"/>
                    </form>
                </sec:authorize>
            </div>
        </div>
</div>
</div>

    </tiles:putAttribute>
</tiles:insertDefinition>
