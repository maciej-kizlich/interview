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

        <datatables:table id="questions" data="${questions}" cdn="true" row="question" theme="bootstrap3"
                          cssClass="table table-striped table-hover" filterable="false">
            <datatables:column title="Firma" cssClass="success">
                    <spring:url value="/company/get/${question.company.id}" var="companyUrl" htmlEscape="true">
                    <spring:param name="companyId" value="${question.company.id}"/>
                </spring:url>
            
                <a href="${fn:escapeXml(companyUrl)}"><c:out value="${question.company.name}"></c:out></a>
            </datatables:column>
            
            <datatables:column title="Stanowisko" cssClass="success">
          ${question.position}
             </datatables:column>
            
            <datatables:column title="Data dodania" cssClass="success">
                <fmt:formatDate value="${question.askDate}" type="DATE" dateStyle="SHORT"/>
            </datatables:column>
            
            <datatables:column title="Pytanie" cssClass="success">
                ${question.question}
            </datatables:column>
            
            <datatables:column title="Ilość odpowiedzi" cssClass="success">
                ${question.answers.size()}
            </datatables:column>
            
            <datatables:column title="Dodał" cssClass="success">
                ${question.user.username}
            </datatables:column>

   <spring:url value="/answers/qAnswers/{questionId}" var="showAnswersUrl" htmlEscape="true">
                        <spring:param name="questionId" value="${question.id}"/>
                    </spring:url>

			<datatables:column cssClass="success">
			<form action="${showAnswersUrl}">
		     	<input type="hidden" value="${question.id}" />
	    		<input class="btn btn-success btn-xs" type="submit" value="Pokaż wszystkie">
			</form>
			
</datatables:column>
		</datatables:table>

    </tiles:putAttribute>
</tiles:insertDefinition>

