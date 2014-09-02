<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">



        You reserved a book successfully!
        <br/>
        Book details: <br/>
        Titile: ${book.title}<br/>
        Author: ${book.author}<br/>

        <br/></br>
        You should return this book on: <b> ${shouldReturnDate} ( TODO :)</b>
        
        <a href="<spring:url value="/books/list" htmlEscape="true" />"> Go to the list of books</a>

    </tiles:putAttribute>
</tiles:insertDefinition>

