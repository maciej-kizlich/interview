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

        System settings:
        <hr />
			<c:forEach items="${configs}" var="entry">
				${entry.description}: ${entry.value} <br/>
			</c:forEach>
        <hr/>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <spring:url value="/admin/editSettings" var="settingsEditUrl" htmlEscape="true" />

			 <input type="button" class="btn btn-default" onclick="location.href='<spring:url value="/admin/showCronJobs"/>'" value="Manage cron job" >

            <form action="${settingsEditUrl}" >
                <input class="btn btn-default" type="submit" value="Edit"/>
            </form>
            
        </sec:authorize>
    </tiles:putAttribute>
</tiles:insertDefinition>