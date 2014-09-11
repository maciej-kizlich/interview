<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        <datatables:table id="questions" data="${companies}" cdn="true" row="company" theme="bootstrap3"
                          cssClass="table table-striped table-hover" filterable="false">
            <datatables:column title="Firma" cssClass="success">
                    <spring:url value="/company/get/${company.id}" var="companyUrl" htmlEscape="true">
                    <spring:param name="companyId" value="${company.id}"/>
                </spring:url>
            
                <a href="${fn:escapeXml(companyUrl)}"><c:out value="${company.name}"></c:out></a>
            </datatables:column>
            
            <datatables:column title="Ilość pytań" cssClass="success">
                ${company.questions.size()}
            </datatables:column>

		</datatables:table>

    </tiles:putAttribute>
</tiles:insertDefinition>

