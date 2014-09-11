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

        <h1>Dane firmy:</h1>
        <hr />

        Nazwa firmy: ${fn:escapeXml(company.name)} <br/>
        
        <hr/>

        <datatables:table id="books" pageable="false" autoWidth="false" data="${company.questions}" sortable="false" cdn="true" theme="bootstrap3" row="question" cssClass="table" filterable="false">
            <datatables:column title="Pytania">
            ${question.question}
            </datatables:column>
                        <datatables:column title="Stanowisko">
            ${question.position}
            </datatables:column>
        </datatables:table>
    </tiles:putAttribute>
</tiles:insertDefinition>
