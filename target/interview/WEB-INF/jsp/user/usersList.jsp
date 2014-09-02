<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

            <datatables:table id="users" data="${users.usersList}" cdn="true" row="user" theme="bootstrap3" cssClass="table table-striped" filterable="false">
              
              <spring:url value="/user/showDetails/{userId}" var="userDetailsUrl" htmlEscape="true">
                  <spring:param name="userId" value="${user.id}"/>
              </spring:url>
              
              <datatables:column title="Username">
               <a href="${fn:escapeXml(userDetailsUrl)}"><c:out value="${user.username}"></c:out></a>
              </datatables:column>
              
              <datatables:column title="Roles">
               <c:forEach var="authority" items="${user.authorities}">
  				  <c:out value="${authority.authority}"/><br/> 
				</c:forEach>
              </datatables:column>
        
              <datatables:column title="Last login">
              <fmt:formatDate value="${user.lastLoginDate}" type="BOTH" dateStyle="FULL" timeStyle="LONG"/>
              </datatables:column>
              
              <datatables:column title="Created/Modified">
              <fmt:formatDate value="${user.userCreationDate}" type="BOTH" dateStyle="FULL" timeStyle="LONG"/>
              </datatables:column>
            
            </datatables:table>

    </tiles:putAttribute>
</tiles:insertDefinition>
